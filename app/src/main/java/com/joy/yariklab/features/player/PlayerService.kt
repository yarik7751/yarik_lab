package com.joy.yariklab.features.player

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.joy.yariklab.R
import com.joy.yariklab.features.music.model.MusicSongUi
import com.joy.yariklab.features.player.model.PlayerCommand
import com.joy.yariklab.features.player.model.PlayerState
import com.joy.yariklab.features.player.observer.PlayerEmitter
import com.joy.yariklab.main.MainActivity
import com.joy.yariklab.toolskit.getParcelableInstance
import com.joy.yariklab.toolskit.intervalTasks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


private const val PLAYER_NOTIFICATION_CHANNEL = "Player notifications"

class PlayerService : Service(), Player.Listener {

    companion object {
        private const val ARGS_PLAYER_COMMAND = "ARGS_PLAYER_COMMAND"
        private const val ARGS_IS_NEED_TO_STOP = "ARGS_IS_NEED_TO_STOP"

        fun newIntent(
            context: Context,
            command: PlayerCommand,
        ): Intent {
            return Intent(context, PlayerService::class.java).apply {
                putExtra(ARGS_PLAYER_COMMAND, command)
            }
        }

        private fun getDeleteIntent(context: Context,) = Intent(context, PlayerService::class.java).apply {
            putExtra(ARGS_IS_NEED_TO_STOP, true)
        }
    }

    private var _exoPlayer: ExoPlayer? = null

    private val exoPlayer: ExoPlayer
        get() = requireNotNull(_exoPlayer) { "ExoPlayer must be initialized!" }

    private var _song: MusicSongUi? = null
    private val song: MusicSongUi
        get() = requireNotNull(_song) { "MusicSongUi must be initialized!" }

    private val playerEmitter: PlayerEmitter by inject()

    private var intervalCoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private var intervalFlow = intervalTasks(
        initDelay = 1000,
        delay = 1000,
        isClosed = false,
    )

    private var isProgressBlocked = false

    override fun onCreate() {
        super.onCreate()
        _exoPlayer = ExoPlayer.Builder(this).build()
        _exoPlayer?.addListener(this)

        intervalCoroutineScope.launch {
            intervalFlow.collect {
                _exoPlayer?.let { exoPlayer ->
                    val song = _song
                    if (exoPlayer.isPlaying && song != null) {
                        sendPlayerEvent()
                    }
                }
            }
        }
    }

    private fun sendPlayerEvent() {
        when {
            isProgressBlocked -> {
                playerEmitter.send(PlayerState.ProgressPause)
                isProgressBlocked = false
            }
            else -> {
                val percent = exoPlayer.getProgressPercent()
                playerEmitter.send(
                    PlayerState.Progress(
                        value = percent,
                        song = song,
                    )
                )
            }
        }
    }

    @Suppress("MagicNumber")
    private fun ExoPlayer.getProgressPercent(): Float {
        return (this.currentPosition.toFloat() / this.duration.toFloat()) * 100F
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val isNeedToStop = intent.getBooleanExtra(ARGS_IS_NEED_TO_STOP, false)
        if (isNeedToStop) {
            playerEmitter.send(PlayerState.Destroy)
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
            return START_NOT_STICKY
        }
        val command = requireNotNull(
            intent.getParcelableInstance(ARGS_PLAYER_COMMAND, PlayerCommand::class.java)
        ) { "Player must receive the command!" }

        when (command) {
            PlayerCommand.Pause -> {
                exoPlayer.pause()
            }

            is PlayerCommand.Play -> {
                if (_song?.url == command.song.url) {
                    exoPlayer.play()
                } else {
                    _song = command.song
                    playSong()
                }
            }

            PlayerCommand.Stop -> {
                exoPlayer.stop()
            }

            is PlayerCommand.ToPosition -> {
                isProgressBlocked = true
                seekTo(command.position)
            }

            PlayerCommand.Nothing -> {}
        }

        createNotificationChannel()
        val pendingIntent = Intent(
            this,
            MainActivity::class.java,
        ).let { notificationIntent ->
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        }

        val deleteIntent = PendingIntent.getService(
            this,
            0,
            getDeleteIntent(this),
            PendingIntent.FLAG_IMMUTABLE,
        )

        val notification = Notification.Builder(this, PLAYER_NOTIFICATION_CHANNEL)
            .setContentTitle(song.title)
            .setContentText(song.subtitle)
            .setSmallIcon(R.drawable.ic_music_note)
            .setContentIntent(pendingIntent)
            .setDeleteIntent(deleteIntent)
            .build()

        startForeground(1, notification)

        return START_NOT_STICKY
    }

    @Suppress("MagicNumber")
    private fun seekTo(percent: Float) {
        val positionInMills = (exoPlayer.duration * (percent / 100F)).toLong()
        when {
            exoPlayer.isPlaying -> {
                exoPlayer.seekTo(positionInMills)
            }
            else -> {
                exoPlayer.apply {
                    seekTo(positionInMills)
                    prepare()
                    play()
                }
            }
        }
    }

    private fun playSong() {
        if (exoPlayer.isPlaying) {
            exoPlayer.stop()
        }
        val currentPosition = song.currentProcess
        if (currentPosition > 0F) {
            seekTo(currentPosition)
        }
        val mediaItem = MediaItem.fromUri(song.url)
        exoPlayer.apply {
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            PLAYER_NOTIFICATION_CHANNEL,
            "Player notifications Channel",
            NotificationManager.IMPORTANCE_DEFAULT,
        )
        serviceChannel.setSound(null, null)
        val manager = getSystemService(
            NotificationManager::class.java
        )
        manager.createNotificationChannel(serviceChannel)
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)

        if (playbackState == Player.STATE_READY) {
            if (exoPlayer.playWhenReady) {
                playerEmitter.send(PlayerState.Play(song))
            } else {
                playerEmitter.send(PlayerState.Pause(song))
            }
        } else if (playbackState == Player.STATE_ENDED) {
            playerEmitter.send(PlayerState.End(song))
        } else {
            playerEmitter.send(PlayerState.Other(song))
        }
    }

    override fun onDestroy() {
        intervalCoroutineScope.cancel()
        _exoPlayer?.stop()
        _exoPlayer?.release()
        _exoPlayer = null
        super.onDestroy()
    }
}
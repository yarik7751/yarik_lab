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
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.joy.yariklab.R
import com.joy.yariklab.features.music.model.MusicSongUi
import com.joy.yariklab.main.MainActivity
import com.joy.yariklab.toolskit.getParcelableInstance


private const val PLAYER_NOTIFICATION_CHANNEL = "Player notifications"

class PlayerService : Service(), Player.Listener {

    companion object {
        private const val ARGS_SONG = "ARGS_SONG"

        fun newIntent(
            context: Context,
            song: MusicSongUi,
        ): Intent {
            return Intent(context, PlayerService::class.java).apply {
                putExtra(ARGS_SONG, song)
            }
        }
    }

    private var _exoPlayer: ExoPlayer? = null

    private val exoPlayer: ExoPlayer
        get() = requireNotNull(_exoPlayer) { "ExoPlayer must be initialized!" }

    private var _song: MusicSongUi? = null
    private val song: MusicSongUi
        get() = requireNotNull(_song) { "MusicSongUi must be initialized!" }

    override fun onCreate() {
        super.onCreate()
        _exoPlayer = ExoPlayer.Builder(this).build()
        _exoPlayer?.addListener(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        _song = intent.getParcelableInstance(ARGS_SONG, MusicSongUi::class.java)

        createNotificationChannel()
        val pendingIntent = Intent(
            this,
            MainActivity::class.java,
        ).let { notificationIntent ->
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        }

        val notification = Notification.Builder(this, PLAYER_NOTIFICATION_CHANNEL)
            .setContentTitle(song.title)
            .setContentText(song.subtitle)
            .setSmallIcon(R.drawable.ic_music_note)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)

        if (exoPlayer.isPlaying) {
            exoPlayer.stop()
        }
        val mediaItem = MediaItem.fromUri(song.url)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()

        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            PLAYER_NOTIFICATION_CHANNEL,
            "Player notifications Channel",
            NotificationManager.IMPORTANCE_DEFAULT,
        )
        val manager = getSystemService(
            NotificationManager::class.java
        )
        manager.createNotificationChannel(serviceChannel)
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)

        when (playbackState) {
            Player.STATE_IDLE -> {}
            Player.STATE_BUFFERING -> {}
            Player.STATE_READY -> {}
            Player.STATE_ENDED -> {}
        }
    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
    }

    override fun onDestroy() {
        _exoPlayer = null
        super.onDestroy()
    }
}
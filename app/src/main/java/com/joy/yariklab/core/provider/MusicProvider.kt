package com.joy.yariklab.core.provider

interface MusicProvider {

    suspend fun getSongs(): List<String>
}

class MusicProviderImpl : MusicProvider {

    override suspend fun getSongs(): List<String> {
        return listOf(
            "https://console.firebase.google.com/project/meetmesimply/storage/meetmesimply.appspot.com/files/~2Fmusic#:~:text=Name-,A_Moment_in_Time_%2D_Graham_Coe.mp3,-Size",
            "https://console.firebase.google.com/project/meetmesimply/storage/meetmesimply.appspot.com/files/~2Fmusic#:~:text=Name-,Lover_%2D_Square_a_Saw.mp3,-Size",
            "https://console.firebase.google.com/project/meetmesimply/storage/meetmesimply.appspot.com/files/~2Fmusic#:~:text=Name-,Monday_8am_%2D_Kinematic.mp3,-Size",
            "https://console.firebase.google.com/project/meetmesimply/storage/meetmesimply.appspot.com/files/~2Fmusic#:~:text=Name-,Tantalizing_Youth_%2D_Social_Square.mp3,-Size",
            "https://console.firebase.google.com/project/meetmesimply/storage/meetmesimply.appspot.com/files/~2Fmusic#:~:text=Name-,Work_N%27_Play_%2D_samiebower_(2).mp3,-Size",
        )
    }
}
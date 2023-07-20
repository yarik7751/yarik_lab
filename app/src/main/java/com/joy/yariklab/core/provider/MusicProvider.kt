package com.joy.yariklab.core.provider

interface MusicProvider {

    suspend fun getSongs(): List<String>
}

class MusicProviderImpl : MusicProvider {

    override suspend fun getSongs(): List<String> {
        return listOf(
            "https://firebasestorage.googleapis.com/v0/b/meetmesimply.appspot.com/o/music%2FA_Moment_in_Time_-_Graham_Coe.mp3?alt=media&token=f950683c-63d4-4ffa-97c7-164cee88c0d2",
            "https://firebasestorage.googleapis.com/v0/b/meetmesimply.appspot.com/o/music%2FLover_-_Square_a_Saw.mp3?alt=media&token=cdceea31-afc1-4654-b25d-fe56c1a0d7d9",
            "https://firebasestorage.googleapis.com/v0/b/meetmesimply.appspot.com/o/music%2FMonday_8am_-_Kinematic.mp3?alt=media&token=9e055ae1-d282-4864-a765-0bd02883ff7e",
            "https://firebasestorage.googleapis.com/v0/b/meetmesimply.appspot.com/o/music%2FTantalizing_Youth_-_Social_Square.mp3?alt=media&token=544eaa13-f73b-44f4-bbbe-c5589917935f",
            "https://firebasestorage.googleapis.com/v0/b/meetmesimply.appspot.com/o/music%2FWork_N'_Play_-_samiebower_(2).mp3?alt=media&token=688a3128-37c9-4963-a3bd-e4992fa309c6",
        )
    }
}
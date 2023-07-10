package com.joy.yariklab.core.domain.interactor

interface MusicInteractor {

    suspend fun getSongs(): List<String>
}
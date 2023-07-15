package com.joy.yariklab.core.domain.interactor

import com.joy.yariklab.core.provider.MusicProvider

class MusicInteractorImpl(
    private val musicProvider: MusicProvider,
) : MusicInteractor {

    override suspend fun getSongs(): List<String> {
        return musicProvider.getSongs()
    }
}
package com.joy.yariklab.core.data

import com.joy.yariklab.archkit.DispatchersProvider
import com.joy.yariklab.core.api.model.joylove.LoginParamsRemote
import com.joy.yariklab.core.api.model.joylove.RegistrationParamsRemote
import com.joy.yariklab.core.api.model.joylove.UserTokensRemote
import com.joy.yariklab.core.api.service.JoyLoveRemoteService
import com.joy.yariklab.core.data.model.joylove.UserTokens
import com.joy.yariklab.core.domain.repository.SignInUpRepository
import com.joy.yariklab.core.local.JoyLoveCache
import kotlinx.coroutines.withContext

class SignInUpRepositoryImpl(
    private val remoteService: JoyLoveRemoteService,
    private val joyLoveCache: JoyLoveCache,
    private val dispatchersProvider: DispatchersProvider,
): SignInUpRepository {

    override suspend fun login(
        login: String,
        password: String,
    ) {
        withContext(dispatchersProvider.background()) {
            remoteService.login(
                LoginParamsRemote(
                    login = login,
                    password = password,
                )
            ).saveTokens()
        }
    }

    override suspend fun register(params: RegistrationParamsRemote) {
        withContext(dispatchersProvider.background()) {
            remoteService.register(params).saveTokens()
        }
    }

    private suspend fun UserTokensRemote.saveTokens() {
        UserTokens(
            token = this.token,
            refreshToken = this.refreshToken,
        ).let {
            joyLoveCache.saveTokens(it)
        }
    }
}
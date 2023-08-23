package com.joy.yariklab.core.domain.repository

interface SignInUpRepository {

    suspend fun login(
        login: String,
        password: String,
    )
}
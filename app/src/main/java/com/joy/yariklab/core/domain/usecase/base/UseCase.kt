package com.joy.yariklab.core.domain.usecase.base

interface UseCase<P, T>  {

    suspend fun execute(params: P): T
}
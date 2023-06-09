package com.joy.yariklab.archkit

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatchersProvider {

    fun background(): CoroutineDispatcher

    fun main(): CoroutineDispatcher
}

class DispatchersProviderImpl : DispatchersProvider {

    override fun background() = Dispatchers.IO

    override fun main() = Dispatchers.Main
}
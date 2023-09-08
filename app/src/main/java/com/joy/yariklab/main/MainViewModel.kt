package com.joy.yariklab.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joy.yariklab.archkit.ViewStateDelegate
import com.joy.yariklab.archkit.ViewStateDelegateImpl
import com.joy.yariklab.core.domain.usecase.IsUserAuthorizedUseCase
import com.joy.yariklab.features.common.ErrorObserver
import com.joy.yariklab.main.MainViewModel.Event
import com.joy.yariklab.navigation.Navigation
import com.joy.yariklab.toolskit.ifNullOrEmpty
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    private val isUserAuthorizedUseCase: IsUserAuthorizedUseCase,
    private val errorObserver: ErrorObserver,
) : ViewModel(), ViewStateDelegate<Unit, Event> by ViewStateDelegateImpl(Unit) {

    sealed interface Event {
        data class ShowError(val title: String, val message: String) : Event
        data class OpenFirstScreen(val destination: String) : Event
    }

    init {
        viewModelScope.launch {
            errorObserver.subscribeOnErrors()
                .onEach { error ->
                    // TODO must be properly transform to informal message
                    sendEvent(
                        Event.ShowError(
                            title = "Error",
                            message = error.message.ifNullOrEmpty { "Some error" },
                        )
                    )
                }
                .launchIn(this)
        }

        viewModelScope.launch {
            val destination = when {
                isUserAuthorizedUseCase.execute(Unit) -> Navigation.UserList.destination
                else -> Navigation.Start.destination
            }
            sendEvent(Event.OpenFirstScreen(destination))
        }
    }
}
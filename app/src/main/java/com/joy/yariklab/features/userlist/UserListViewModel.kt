package com.joy.yariklab.features.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joy.yariklab.archkit.ViewStateDelegate
import com.joy.yariklab.archkit.ViewStateDelegateImpl
import com.joy.yariklab.archkit.safeLaunch
import com.joy.yariklab.core.domain.interactor.LoveInteractor
import com.joy.yariklab.features.common.ErrorEmitter
import com.joy.yariklab.features.userlist.UserListViewModel.Event
import com.joy.yariklab.features.userlist.UserListViewModel.ViewState

class UserListViewModel(
    private val loveInteractor: LoveInteractor,
    private val errorEmitter: ErrorEmitter,
) : ViewModel(), ViewStateDelegate<ViewState, Event> by ViewStateDelegateImpl(ViewState()) {

    data class ViewState(
        val isLoading: Boolean = false,
    )

    sealed interface Event

    init {
        viewModelScope.safeLaunch(errorEmitter::emit) {
            val users = loveInteractor.getUsersForLove()
            users.hashCode()
        }
    }
}
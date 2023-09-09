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
import com.joy.yariklab.features.userlist.model.UserForLoveUi
import com.joy.yariklab.features.userlist.model.UserListStatus

class UserListViewModel(
    private val loveInteractor: LoveInteractor,
    private val errorEmitter: ErrorEmitter,
) : ViewModel(), ViewStateDelegate<ViewState, Event> by ViewStateDelegateImpl(ViewState()) {

    data class ViewState(
        val isLoading: Boolean = false,
        val usersForLove: List<UserForLoveUi> = emptyList(),
        val currentUser: UserForLoveUi? = null,
        val screenStatus: UserListStatus = UserListStatus.INIT,
    )

    sealed interface Event

    init {
        viewModelScope.safeLaunch(errorEmitter::emit) {
            reduce {
                it.copy(isLoading = false)
            }
            val users = loveInteractor.getUsersForLove().map { domain ->
                UserForLoveUi(
                    id = domain.id,
                    age = domain.age,
                    contacts = domain.contacts,
                    email = domain.email,
                    logoUrl = domain.logoUrl,
                    videoUrl = domain.videoUrl,
                    mobilePhone = domain.mobilePhone,
                    name = domain.name,
                    rating = domain.rating,
                    sex = domain.sex,
                    isCurrent = false,
                    isLiked = false,
                )
            }
            var currentUser: UserForLoveUi? = null
            val screenStatus = when {
                users.isEmpty() -> UserListStatus.EMPTY_LIST
                else -> {
                    currentUser = users.first()
                    UserListStatus.USERS
                }
            }
            reduce {
                it.copy(
                    usersForLove = users,
                    currentUser = currentUser,
                    screenStatus = screenStatus,
                )
            }
        }.invokeOnCompletion {
            viewModelScope.reduce {
                it.copy(isLoading = false)
            }
        }
    }

    fun onLikeClick() {
        viewModelScope.safeLaunch(errorEmitter::emit) {
            stateValue.currentUser?.let { currentUser ->
                val likeUserId = currentUser.id
                val likeResult = loveInteractor.likeUser(likeUserId)
                when {
                    likeResult.isMeetingCreated -> {
                        val lastIndex = stateValue.usersForLove.size - 1
                        var newCurrentUser: UserForLoveUi? = null
                        val newUsers = stateValue.usersForLove.mapIndexed { index, userForLoveUi ->
                            when (userForLoveUi.id) {
                                currentUser.id -> {
                                    val newCurrentUserIndex = if (index == lastIndex) 0 else index + 1
                                    newCurrentUser = stateValue.usersForLove[newCurrentUserIndex]
                                    userForLoveUi.copy(isLiked = true)
                                }
                                else -> userForLoveUi
                            }
                        }
                        val screenStatus = when {
                            newCurrentUser == null -> UserListStatus.EMPTY_LIST
                            newCurrentUser?.isLiked == true -> UserListStatus.EMPTY_LIST
                            else -> UserListStatus.USERS
                        }

                        reduce {
                            it.copy(
                                currentUser = newCurrentUser,
                                usersForLove = newUsers,
                                screenStatus = screenStatus,
                            )
                        }
                    }
                    likeResult.isMeetingConfirmed -> {

                    }
                }
            }
        }
    }

    fun onSkipClick() {

    }
}
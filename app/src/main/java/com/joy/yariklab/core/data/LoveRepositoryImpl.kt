package com.joy.yariklab.core.data

import com.joy.yariklab.archkit.DispatchersProvider
import com.joy.yariklab.core.api.model.joylove.user.LikeUserParamsRemote
import com.joy.yariklab.core.api.service.JoyLoveRemoteService
import com.joy.yariklab.core.data.model.joylove.LikeUserResult
import com.joy.yariklab.core.data.model.joylove.UserForLove
import com.joy.yariklab.core.domain.repository.LoveRepository
import com.joy.yariklab.core.local.JoyLoveCache
import kotlinx.coroutines.withContext

class LoveRepositoryImpl(
    private val remoteService: JoyLoveRemoteService,
    private val joyLoveCache: JoyLoveCache,
    private val dispatchersProvider: DispatchersProvider,
): LoveRepository {
    override suspend fun getUsersForLove(): List<UserForLove> {
        // TODO params are needed to be from cache
        return withContext(dispatchersProvider.background()) {
            remoteService.getUsersForLove(
                distance = 5,
                sex = 1,
                ageMin = 18,
                ageMax = 35,
            ).map { remote ->
                UserForLove(
                    id = remote.id,
                    age = remote.age,
                    contacts = remote.contacts.orEmpty(),
                    email = remote.email,
                    logoUrl = remote.logoUrl,
                    videoUrl = remote.videoUrl,
                    mobilePhone = remote.mobilePhone,
                    name = remote.name,
                    rating = remote.rating,
                    sex = remote.sex,
                )
            }
        }
    }

    override suspend fun likeUser(likeUserId: Int): LikeUserResult {
        val params = LikeUserParamsRemote(likeUserId)
        return withContext(dispatchersProvider.background()) {
            remoteService.likeUser(params).let { remote ->
                LikeUserResult(
                    isMeetingConfirmed = remote.isMeetingConfirmed,
                    isMeetingCreated = remote.isMeetingCreated,
                )
            }
        }
    }
}
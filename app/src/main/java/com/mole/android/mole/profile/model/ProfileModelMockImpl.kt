package com.mole.android.mole.profile.model

import android.util.Log
import com.mole.android.mole.profile.data.ProfileUserInfo
import com.mole.android.mole.web.service.ApiResult
import kotlinx.coroutines.*
import retrofit2.HttpException

class ProfileModelMockImpl(
    private val mainScope: CoroutineScope
) : ProfileModel {

    override suspend fun getProfileInfo(): ApiResult<ProfileModel.SuccessProfileResult> {
        val task = mainScope.async(Dispatchers.IO) {
            try {
                val profileUserInfoDomain = ProfileService.MOCK
                delay(1500)
                Log.i("Profile", "Profile User Info: $profileUserInfoDomain")
                ApiResult.create(
                    ProfileModel.SuccessProfileResult(
                        ProfileUserInfo(
                            profileUserInfoDomain
                        )
                    )
                )
            } catch (exception: HttpException) {
                // Не хочется падать если что-то не так на сервере
                ApiResult.create(
                    ApiResult.MoleError(
                        exception.code(),
                        exception.message()
                    )
                )
            }
        }
        return task.await()
    }

    override suspend fun setProfileInfo(name: String, login: String?) {
        mainScope.launch {
            delay(1500)
            Log.d("Profile", "Set profile data: name = $name, login = $login")
        }
    }
}

package com.mole.android.mole.profile.model

import android.net.Uri
import android.util.Log
import com.mole.android.mole.profile.data.ProfileData
import com.mole.android.mole.profile.data.ProfilePhoto
import com.mole.android.mole.profile.data.ProfileUserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class ProfileModelImpl(
    private val service: ProfileService,
    private val mainScope: CoroutineScope
) : ProfileModel {

    private val emptyData = ProfileUserInfo(
        ProfileData(
            0, "", "", "", ""
        ), ProfilePhoto(
            Uri.EMPTY, Uri.EMPTY
        )
    )

    override suspend fun getProfileInfo(): ProfileUserInfo {
        val task = mainScope.async(Dispatchers.IO) {
            try {
                service.getProfileInfo()
            } catch (exception: Exception) {
                // Не хочется падать если что-то не так на сервере
                exception.printStackTrace()
                emptyData
            }
        }
        val profileUserInfo: ProfileUserInfo = task.await()
        Log.i("Profile", "Profile User Info: $profileUserInfo")
        return profileUserInfo
    }

}
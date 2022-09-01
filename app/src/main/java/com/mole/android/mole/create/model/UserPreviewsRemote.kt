package com.mole.android.mole.create.model

import com.google.gson.annotations.SerializedName
import com.mole.android.mole.create.data.SuccessPreviewsResult
import com.mole.android.mole.profile.data.ProfilePhoto

data class UserPreviewsRemote(
    @SerializedName("debtor")
    val previews: List<UserPreviewRemote>
)

data class UserPreviewRemote(
    @SerializedName("debtorInfo")
    val userInfo: UserInfoRemote,
    @SerializedName("mainPhotoUrl")
    val photo: PhotoUrlRemote
)

data class PhotoUrlRemote(
    @SerializedName("photoSmall")
    val smallUrl: String = "",
    @SerializedName("photoNormal")
    val normalUrl: String = ""
)

data class UserInfoRemote(
    @SerializedName("idUser")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("login")
    val login: String = ""

)

fun UserPreviewsRemote.asDomain(): SuccessPreviewsResult = previews.map { remote ->
    UserPreview(
        id = remote.userInfo.id,
        name = remote.userInfo.name,
        login = remote.userInfo.login,
        avatar = ProfilePhoto(
            photoSmall = remote.photo.smallUrl,
            photoNormal = remote.photo.normalUrl
        )
    )
}
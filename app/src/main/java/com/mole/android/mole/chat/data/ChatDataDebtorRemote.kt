package com.mole.android.mole.chat.data

import com.google.gson.annotations.SerializedName

data class ChatDataDebtorRemote(
    @SerializedName("debtorInfo")
    val debtorInfo: ChatDebtorInfoRemote,
    @SerializedName("debtorStatistic")
    val debtorStatistic: ChatDebtorStatisticRemote,
    @SerializedName("mainPhotoUrl")
    val mainPhotoUrl: ChatDebtorPhotoUrlRemote
)

data class ChatDebtorInfoRemote(
    @SerializedName("idUser")
    val idUser: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("mainPhotoName")
    val mainPhotoName: String
)

data class ChatDebtorStatisticRemote(
    @SerializedName("debtSum")
    val debtSum: Int,
    @SerializedName("debtCount")
    val debtCount: Int
)

data class ChatDebtorPhotoUrlRemote(
    @SerializedName("photoSmall")
    val photoSmall: String,
    @SerializedName("photoNormal")
    val photoNormal: String
)

fun ChatDataDebtorRemote.asDomain(): ChatDataDebtorDomain {
    return ChatDataDebtorDomain(
        id = debtorInfo.idUser,
        name = debtorInfo.name,
        avatarUrl = mainPhotoUrl.photoSmall,
        balance = debtorStatistic.debtSum
    )
}
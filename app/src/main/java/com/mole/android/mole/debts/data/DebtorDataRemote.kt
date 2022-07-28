package com.mole.android.mole.debts.data

import com.google.gson.annotations.SerializedName

data class DebtorDataRemote(
    @SerializedName("debtorInfo")
    val debtorInfo: DebtorInfoRemote,
    @SerializedName("debtorStatistic")
    val debtorStatistic: DebtorStatisticRemote,
    @SerializedName("mainPhotoUrl")
    val mainPhotoUrl: MainPhotoUrlRemote
)

data class DebtorInfoRemote(
    @SerializedName("idUser")
    val idUser: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("mainPhotoName")
    val mainPhotoName: String
)

data class DebtorStatisticRemote(
    @SerializedName("debtSum")
    val debtSum: Int,
    @SerializedName("debtCount")
    val debtCount: Int
)

data class MainPhotoUrlRemote(
    @SerializedName("photoSmall")
    val photoSmall: String,
    @SerializedName("photoNormal")
    val photoNormal: String
)

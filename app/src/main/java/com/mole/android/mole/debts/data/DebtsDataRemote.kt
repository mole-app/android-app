package com.mole.android.mole.debts.data

import com.google.gson.annotations.SerializedName

data class DebtsDataRemote(
    @SerializedName("debtSum")
    val debtsSum: Int?,
    @SerializedName("debtor")
    val debtors: List<DebtorDataRemote>?
)

fun DebtsDataRemote.asDomain(): DebtsData {
    return DebtsData(
        debtsSumTotal = debtsSum ?: 0,
        debtors = if (this.debtors.isNullOrEmpty()) {
            listOf()
        } else {
            this.debtors.map { debtorDataRemote ->
                DebtorData(
                    id = debtorDataRemote.debtorInfo.idUser,
                    name = debtorDataRemote.debtorInfo.name,
                    debtsCount = debtorDataRemote.debtorStatistic.debtCount,
                    debtsSum = debtorDataRemote.debtorStatistic.debtSum,
                    imageUrl = debtorDataRemote.mainPhotoUrl.photoNormal
                )
            }
        }
    )
}

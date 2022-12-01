package com.mole.android.mole.repay.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepayData(
    val userId: String = "",
    val currentDebt: Int = 0,
    val allDebts: Int = 0
) : Parcelable

package com.mole.android.mole.repay.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepayData(
    val userId: Int,
    val allDebtsSum: Int,
    val userName: String,
    val userIconUrl: String,
) : Parcelable

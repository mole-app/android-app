package com.mole.android.mole.repay.data

import android.os.Parcelable
import com.mole.android.mole.chat.data.DebtType
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepayData(
    val userId: Int,
    val allDebtsSum: Int,
    val debtType: DebtType
) : Parcelable

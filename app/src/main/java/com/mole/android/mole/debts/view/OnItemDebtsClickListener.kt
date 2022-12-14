package com.mole.android.mole.debts.view

import android.view.View
import com.mole.android.mole.debts.data.DebtorData
import com.mole.android.mole.debts.data.DebtsData

interface OnItemDebtsClickListener {
    fun onLongClick(view: View, chatData: DebtorData)
    fun onShotClick(chatData: DebtorData)
}
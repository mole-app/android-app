package com.mole.android.mole.debts.view

import com.mole.android.mole.MoleBaseView
import com.mole.android.mole.debts.data.DebtorData
import com.mole.android.mole.debts.data.DebtsData

interface DebtsView : MoleBaseView {
    fun showRepayScreen(data: DebtorData)
    fun showChatScreen(idDebtor: Int)
}
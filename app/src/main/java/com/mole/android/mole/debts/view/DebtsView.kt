package com.mole.android.mole.debts.view

import com.mole.android.mole.MoleBaseView
import com.mole.android.mole.debts.data.DebtorData
import com.mole.android.mole.debts.data.DebtsData

interface DebtsView : MoleBaseView {
//    fun setData(data: DebtsData)
//    fun showLoading(isVisible: Boolean)
//    fun showError(code: Int, description: String)
    fun showRepayScreen(data: DebtorData)
    fun showChatScreen(idDebtor: Int)
}
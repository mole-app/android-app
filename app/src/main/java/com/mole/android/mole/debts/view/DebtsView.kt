package com.mole.android.mole.debts.view

import com.mole.android.mole.MoleBaseView
import com.mole.android.mole.debts.data.DebtsData

interface DebtsView : MoleBaseView {
    fun setData(data: List<DebtsData>)
    fun showLoading()
    fun hideLoading()
    fun showError(code: Int, description: String)
    fun showDeleteDialog()
    fun showChatScreen(idDebtor: Int)
}
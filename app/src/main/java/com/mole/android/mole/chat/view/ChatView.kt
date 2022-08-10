package com.mole.android.mole.chat.view

import com.mole.android.mole.MoleBaseView
import com.mole.android.mole.chat.data.ChatDebtsDataUi
import com.mole.android.mole.chat.data.ChatDataDebtorDomain

interface ChatView: MoleBaseView {
    fun setData(data: List<ChatDebtsDataUi>)
    fun setToolbarLoading()
    fun setToolbarData(data: ChatDataDebtorDomain)
    fun showLoading()
    fun hideLoading()
    fun showCreateDebtScreen(userId: Int)
}
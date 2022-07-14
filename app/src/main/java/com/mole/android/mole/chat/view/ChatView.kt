package com.mole.android.mole.chat.view

import com.mole.android.mole.MoleBaseView
import com.mole.android.mole.chat.data.ChatDebtsData
import com.mole.android.mole.chat.data.ChatUserInfo

interface ChatView: MoleBaseView {
    fun setData(data: List<ChatDebtsData>)
    fun setToolbarLoading()
    fun setToolbarData(data: ChatUserInfo)
    fun showLoading()
    fun hideLoading()
    fun getUserId() : Int
    fun getIdDebtMax(): Int?
}
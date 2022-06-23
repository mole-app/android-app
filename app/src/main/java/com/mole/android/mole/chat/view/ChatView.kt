package com.mole.android.mole.chat.view

import com.mole.android.mole.MoleBaseView
import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.chat.data.ChatUserInfo

interface ChatView: MoleBaseView {
    fun setData(data: List<ChatData>)
    fun setToolbarLoading()
    fun setToolbarData(data: ChatUserInfo)
    fun showLoading()
    fun hideLoading()
    fun getUserId() : Int
}
package com.mole.android.mole.chat.view

import com.mole.android.mole.chat.data.ChatData

interface ChatView {
    fun setData(data: List<ChatData>)
    fun showLoading()
    fun hideLoading()
}
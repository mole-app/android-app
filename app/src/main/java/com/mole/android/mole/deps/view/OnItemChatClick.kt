package com.mole.android.mole.deps.view

import com.mole.android.mole.deps.data.ChatData

interface OnItemChatClickListener {
    fun onLongClick(chatData: ChatData)
    fun onShotClick(chatData: ChatData)
}
package com.mole.android.mole.debts.view

import com.mole.android.mole.debts.data.ChatData

interface OnItemChatClickListener {
    fun onLongClick(chatData: ChatData)
    fun onShotClick(chatData: ChatData)
}
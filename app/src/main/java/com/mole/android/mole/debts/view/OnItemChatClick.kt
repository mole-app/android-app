package com.mole.android.mole.debts.view

import android.view.View
import com.mole.android.mole.debts.data.DebtsData

interface OnItemChatClickListener {
    fun onLongClick(view: View, chatData: DebtsData.ChatDebtsData)
    fun onShotClick(chatData: DebtsData.ChatDebtsData)
}
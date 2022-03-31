package com.mole.android.mole.chat.view.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.chat.view.Binder
import com.mole.android.mole.databinding.ItemChatMessageEndPositionBinding

class MessageOfTheDebtorViewHolder(val binding: ItemChatMessageEndPositionBinding) :
    RecyclerView.ViewHolder(binding.root), Binder<ChatData.ChatMessage> {
    override fun bind(data: ChatData.ChatMessage) {
        binding.messageEndPosition.balance = data.debtValue
    }
}
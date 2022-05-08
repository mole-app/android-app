package com.mole.android.mole.chat.view.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.chat.view.Binder
import com.mole.android.mole.databinding.ItemChatMessageStartPositionBinding

class MessageOfOtherDebtorViewHolder(val binding: ItemChatMessageStartPositionBinding) :
    RecyclerView.ViewHolder(binding.root), Binder<ChatData.ChatMessage> {
    override fun bind(data: ChatData.ChatMessage) {
        with(binding.messageStartPosition){
            balance = data.debtValue
            tag = data.tag
            time = data.time
        }
    }
}
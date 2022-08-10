package com.mole.android.mole.chat.view.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.chat.data.ChatDebtsDataUi
import com.mole.android.mole.MoleBinder
import com.mole.android.mole.databinding.ItemChatMessageStartPositionBinding

class MessageOfUserViewHolder(val binding: ItemChatMessageStartPositionBinding) :
    RecyclerView.ViewHolder(binding.root), MoleBinder<ChatDebtsDataUi.ChatMessage> {
    override fun bind(data: ChatDebtsDataUi.ChatMessage) {
        with(binding.messageStartPosition){
            balance = data.debtValue
            tag = data.tag
            time = data.time
        }
    }
}
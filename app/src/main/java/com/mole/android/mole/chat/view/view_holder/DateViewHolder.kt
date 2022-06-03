package com.mole.android.mole.chat.view.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.MoleBinder
import com.mole.android.mole.databinding.ItemChatDateBinding

class DateViewHolder(val binding: ItemChatDateBinding) : RecyclerView.ViewHolder(binding.root),
    MoleBinder<ChatData.ChatDate> {
    override fun bind(data: ChatData.ChatDate) {
        binding.chatDate.text = data.date
    }
}
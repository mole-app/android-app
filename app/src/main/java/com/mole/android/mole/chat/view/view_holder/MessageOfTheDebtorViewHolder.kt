package com.mole.android.mole.chat.view.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.MoleBinder
import com.mole.android.mole.PopupProvider
import com.mole.android.mole.databinding.ItemChatMessageEndPositionBinding

class MessageOfTheDebtorViewHolder(
    val binding: ItemChatMessageEndPositionBinding,
    private val popupProvider: PopupProvider? = null
) :
    RecyclerView.ViewHolder(binding.root), MoleBinder<ChatData.ChatMessage> {

    init {
        if (popupProvider != null) {
            with(binding.messageEndPosition) {
                setOnTouchListener(popupProvider.touchListener)
                setOnLongClickListener {
                    popupProvider.start(it, PopupProvider.Position.RIGHT)
                    true
                }
            }
        }
    }

    override fun bind(data: ChatData.ChatMessage) {
        with(binding.messageEndPosition) {
            balance = data.debtValue
            tag = data.tag
            time = data.time
            isRead = data.isRead
        }
    }
}
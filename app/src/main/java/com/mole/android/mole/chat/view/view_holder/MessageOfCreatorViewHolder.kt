package com.mole.android.mole.chat.view.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.chat.data.ChatDebtsDataUi
import com.mole.android.mole.MoleBinder
import com.mole.android.mole.PopupProvider
import com.mole.android.mole.databinding.ItemChatMessageEndPositionBinding

class MessageOfCreatorViewHolder(
    val binding: ItemChatMessageEndPositionBinding,
    private val popupProvider: PopupProvider<Int>? = null
) :
    RecyclerView.ViewHolder(binding.root), MoleBinder<ChatDebtsDataUi.ChatMessage> {
    private var currentId: Int = -1

    init {
        if (popupProvider != null) {
            with(binding.messageEndPosition) {
                setOnTouchListener(popupProvider.touchListener)
                setOnLongClickListener {
                    popupProvider.start(it, currentId, PopupProvider.Position.RIGHT)
                    true
                }
            }
        }
    }

    override fun bind(data: ChatDebtsDataUi.ChatMessage) {
        with(binding.messageEndPosition){
            balance = data.debtValue
            tag = data.tag
            time = data.time
            isRead = data.isRead
            isDeleted = data.isDeleted
        }
        currentId = data.id
    }
}
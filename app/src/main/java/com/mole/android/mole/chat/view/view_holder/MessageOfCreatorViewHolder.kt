package com.mole.android.mole.chat.view.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.chat.data.ChatDebtsDataUi
import com.mole.android.mole.MoleBinder
import com.mole.android.mole.PopupProvider
import com.mole.android.mole.databinding.ItemChatMessageEndPositionBinding

class MessageOfCreatorViewHolder(
    val binding: ItemChatMessageEndPositionBinding,
    private val popupProvider: PopupProvider<Int>? = null
) :
    RecyclerView.ViewHolder(binding.root), MoleBinder<ChatDebtsDataUi.ChatMessage>,
    View.OnClickListener {
    private var currentId: Int = -1

    init {
        if (popupProvider != null) {
            with(binding.messageEndPosition) {
                setOnTouchListener(popupProvider.touchListener)
            }
        }
    }

    override fun bind(data: ChatDebtsDataUi.ChatMessage) {
        with(binding.messageEndPosition) {
            balance = data.debtValue
            tag = data.tag
            time = data.time
            isRead = data.isRead
            isDeleted = data.isDeleted
            if (isDeleted) {
                binding.messageEndPosition.setOnClickListener(null)
            } else {
                binding.messageEndPosition.setOnClickListener(this@MessageOfCreatorViewHolder)
            }
        }
        currentId = data.id
    }

    override fun onClick(view: View) {
        popupProvider?.start(view, currentId, PopupProvider.Position.RIGHT)
    }
}

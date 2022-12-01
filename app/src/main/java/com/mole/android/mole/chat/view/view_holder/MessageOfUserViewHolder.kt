package com.mole.android.mole.chat.view.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.chat.data.ChatDebtsDataUi
import com.mole.android.mole.MoleBinder
import com.mole.android.mole.PopupProvider
import com.mole.android.mole.databinding.ItemChatMessageStartPositionBinding

class MessageOfUserViewHolder(
    val binding: ItemChatMessageStartPositionBinding,
    private val popupProvider: PopupProvider<Int>? = null
) :
    RecyclerView.ViewHolder(binding.root), MoleBinder<ChatDebtsDataUi.ChatMessage>,
    View.OnClickListener {

    private var currentId: Int = -1

    init {
        if (popupProvider != null) {
            with(binding.messageStartPosition) {
                setOnTouchListener(popupProvider.touchListener)
            }
        }
    }

    override fun bind(data: ChatDebtsDataUi.ChatMessage) {
        with(binding.messageStartPosition) {
            balance = data.debtValue
            tag = data.tag
            time = data.time
            isDeleted = data.isDeleted
            if (isDeleted) {
                binding.messageStartPosition.setOnClickListener(null)
            } else {
                binding.messageStartPosition.setOnClickListener(this@MessageOfUserViewHolder)
            }
        }
        currentId = data.id
    }

    override fun onClick(view: View) {
        popupProvider?.start(view, currentId, PopupProvider.Position.LEFT)
    }
}

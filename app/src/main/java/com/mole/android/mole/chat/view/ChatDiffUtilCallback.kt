package com.mole.android.mole.chat.view

import androidx.recyclerview.widget.DiffUtil
import com.mole.android.mole.chat.data.ChatDebtsDataUi

class ChatDiffUtilCallback(
    private val oldList: List<ChatDebtsDataUi>,
    private val newList: List<ChatDebtsDataUi>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newList[newItemPosition]
        val oldItem = oldList[oldItemPosition]
        return when (newItem) {
            is ChatDebtsDataUi.ChatDate -> {
                oldItem is ChatDebtsDataUi.ChatDate
            }
            is ChatDebtsDataUi.ChatMessage -> {
                if (oldItem is ChatDebtsDataUi.ChatMessage) {
                    oldItem.id == newItem.id
                } else false
            }
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newList[newItemPosition]
        val oldItem = oldList[oldItemPosition]
        return when (newItem) {
            is ChatDebtsDataUi.ChatMessage -> {
                if (oldItem is ChatDebtsDataUi.ChatMessage) {
                    newItem == oldItem
                } else false
            }
            is ChatDebtsDataUi.ChatDate -> {
                if (oldItem is ChatDebtsDataUi.ChatDate) {
                    newItem.date == oldItem.date
                } else false
            }
        }
    }
}
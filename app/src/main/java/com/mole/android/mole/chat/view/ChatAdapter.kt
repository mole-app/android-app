package com.mole.android.mole.chat.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.chat.view.view_holder.DateViewHolder
import com.mole.android.mole.chat.view.view_holder.MessageOfOtherDebtorViewHolder
import com.mole.android.mole.chat.view.view_holder.MessageOfTheDebtorViewHolder
import com.mole.android.mole.databinding.ItemChatDateBinding
import com.mole.android.mole.databinding.ItemChatMessageEndPositionBinding
import com.mole.android.mole.databinding.ItemChatMessageStartPositionBinding

class ChatAdapter : BaseRecyclerAdapter<ChatData>() {

    private companion object ChatDataType {
        private const val DATE = 0
        private const val MESSAGE_OF_THE_DEBTOR = 1
        private const val MESSAGE_OF_OTHER_DEBTOR = -1
    }

    override fun getViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return when (viewType) {
            MESSAGE_OF_THE_DEBTOR -> {
                MessageOfTheDebtorViewHolder(
                    ItemChatMessageEndPositionBinding.inflate(
                        layoutInflater,
                        viewGroup,
                        false
                    )
                )
            }
            MESSAGE_OF_OTHER_DEBTOR -> {
                MessageOfOtherDebtorViewHolder(
                    ItemChatMessageStartPositionBinding.inflate(
                        layoutInflater,
                        viewGroup,
                        false
                    )
                )
            }
            else -> DateViewHolder(
                ItemChatDateBinding.inflate(
                    layoutInflater,
                    viewGroup,
                    false
                )
            )
        }
    }

    override fun getViewTypeOfData(position: Int): Int {
        return when (data[position]) {
            is ChatData.ChatMessage -> {
                if ((data[position] as ChatData.ChatMessage).isMessageOfTheDebtor) MESSAGE_OF_THE_DEBTOR
                else MESSAGE_OF_OTHER_DEBTOR
            }
            is ChatData.ChatDate -> DATE
        }
    }

    fun setData(data: List<ChatData>){
        update(data)
    }
}
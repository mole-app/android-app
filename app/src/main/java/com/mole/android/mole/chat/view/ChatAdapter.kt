package com.mole.android.mole.chat.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.MoleBaseRecyclerAdapter
import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.chat.view.view_holder.DateViewHolder
import com.mole.android.mole.chat.view.view_holder.MessageOfUserViewHolder
import com.mole.android.mole.chat.view.view_holder.MessageOfCreatorViewHolder
import com.mole.android.mole.databinding.ItemChatDateBinding
import com.mole.android.mole.databinding.ItemChatMessageEndPositionBinding
import com.mole.android.mole.databinding.ItemChatMessageStartPositionBinding

class ChatAdapter : MoleBaseRecyclerAdapter<ChatData>() {

    private companion object ChatDataType {
        private const val DATE = 0
        private const val MESSAGE_OF_CREATOR = 1
        private const val MESSAGE_OF_USER = -1
    }

    override fun getViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return when (viewType) {
            MESSAGE_OF_CREATOR -> {
                MessageOfCreatorViewHolder(
                    ItemChatMessageEndPositionBinding.inflate(
                        layoutInflater,
                        viewGroup,
                        false
                    )
                )
            }
            MESSAGE_OF_USER -> {
                MessageOfUserViewHolder(
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
                if ((data[position] as ChatData.ChatMessage).isMessageOfUser) MESSAGE_OF_CREATOR
                else MESSAGE_OF_USER
            }
            is ChatData.ChatDate -> DATE
        }
    }

    fun setChatData(data: List<ChatData>){
        update(data)
    }
}
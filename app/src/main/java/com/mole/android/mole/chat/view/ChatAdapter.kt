package com.mole.android.mole.chat.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.MoleBaseRecyclerAdapter
import com.mole.android.mole.PopupProvider
import com.mole.android.mole.chat.data.ChatDebtsDataUi
import com.mole.android.mole.chat.view.view_holder.DateViewHolder
import com.mole.android.mole.chat.view.view_holder.MessageOfCreatorViewHolder
import com.mole.android.mole.chat.view.view_holder.MessageOfUserViewHolder
import com.mole.android.mole.chat.view.view_holder.RetryViewHolder
import com.mole.android.mole.databinding.ItemChatDateBinding
import com.mole.android.mole.databinding.ItemChatMessageEndPositionBinding
import com.mole.android.mole.databinding.ItemChatMessageStartPositionBinding
import com.mole.android.mole.databinding.ItemChatRetryBtnBinding

class ChatAdapter(
    private val popupProvider: PopupProvider<Int>? = null,
    private val retryBtnListener: RetryButtonClickListener
) :
    MoleBaseRecyclerAdapter<ChatDebtsDataUi>() {

    private companion object ChatDataType {
        private const val DATE = 0
        private const val MESSAGE_OF_CREATOR = 1
        private const val MESSAGE_OF_USER = -1
        private const val ERROR_LOAD_RETRY_BTN = 2
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
                    ),
                    popupProvider
                )
            }
            MESSAGE_OF_USER -> {
                MessageOfUserViewHolder(
                    ItemChatMessageStartPositionBinding.inflate(
                        layoutInflater,
                        viewGroup,
                        false
                    ),
                    popupProvider
                )
            }
            ERROR_LOAD_RETRY_BTN -> {
                RetryViewHolder(
                    ItemChatRetryBtnBinding.inflate(
                        layoutInflater,
                        viewGroup,
                        false
                    ),
                    retryBtnListener
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
        return when (getPositionData(position)) {
            is ChatDebtsDataUi.ChatMessage -> {
                if ((getPositionData(position) as ChatDebtsDataUi.ChatMessage).isMessageOfCreator) MESSAGE_OF_CREATOR
                else MESSAGE_OF_USER
            }
            is ChatDebtsDataUi.ChatDate -> DATE
            is ChatDebtsDataUi.RetryData -> ERROR_LOAD_RETRY_BTN
        }
    }
}
package com.mole.android.mole.chat.view.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.MoleBinder
import com.mole.android.mole.chat.data.ChatDebtsDataUi
import com.mole.android.mole.chat.view.RetryButtonClickListener
import com.mole.android.mole.databinding.ItemChatRetryBtnBinding

class RetryViewHolder(
    val binding: ItemChatRetryBtnBinding,
    private val listener: RetryButtonClickListener
) : RecyclerView.ViewHolder(binding.root),
    MoleBinder<ChatDebtsDataUi.RetryData> {
    override fun bind(data: ChatDebtsDataUi.RetryData) {
        binding.retryButton.setOnClickListener {
            listener.onRetryButtonClicked()
        }
    }
}

package com.mole.android.mole.chat.view.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.MoleBinder
import com.mole.android.mole.Shape
import com.mole.android.mole.chat.data.ChatDebtsDataUi
import com.mole.android.mole.chat.view.RetryButtonClickListener
import com.mole.android.mole.databinding.ItemChatRetryBtnBinding
import com.mole.android.mole.dp
import com.mole.android.mole.setupBorder

class RetryViewHolder(
    val binding: ItemChatRetryBtnBinding,
    private val listener: RetryButtonClickListener
) : RecyclerView.ViewHolder(binding.root),
    MoleBinder<ChatDebtsDataUi.RetryData> {

    init {
        binding.retryButton.setupBorder(Shape.RECTANGLE, 80f.dp)
    }

    override fun bind(data: ChatDebtsDataUi.RetryData) {
        binding.retryButton.setOnClickListener {
            listener.onRetryButtonClicked()
        }
    }
}

package com.mole.android.mole.chat.view.view_holder

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.MoleBinder
import com.mole.android.mole.R
import com.mole.android.mole.chat.data.ChatDebtsDataUi
import com.mole.android.mole.databinding.ItemChatRepayDebtBinding
import com.mole.android.mole.isPositive
import com.mole.android.mole.resolveColor
import kotlin.math.absoluteValue

class RepayDebtViewHolder(private val binding: ItemChatRepayDebtBinding)
    : RecyclerView.ViewHolder(binding.root), MoleBinder<ChatDebtsDataUi.RepayDebt> {

    override fun bind(data: ChatDebtsDataUi.RepayDebt) {
        val context = binding.root.context
        val textView = binding.root
        val spanColor = context.resolveColor(R.attr.textColorPrimary)
        val content = if (data.amount.isPositive()) {
            context.getString(R.string.user_repay_debt_current_user, data.amount.absoluteValue)
        } else {
            context.getString(R.string.user_repay_debt, data.userName, data.amount.absoluteValue)
        }

        val ixOfFirstDigit = content.indexOfFirst { it.isDigit() }
        val spannable = SpannableString(content)
        spannable.setSpan(
            ForegroundColorSpan(spanColor),
            0,
            if (data.amount.isPositive()) 2 else data.userName.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannable.setSpan(
            ForegroundColorSpan(spanColor),
            ixOfFirstDigit,
            content.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textView.text = spannable
    }

}
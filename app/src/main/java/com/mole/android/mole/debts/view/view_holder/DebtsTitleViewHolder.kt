package com.mole.android.mole.debts.view.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.MoleBinder
import com.mole.android.mole.R
import com.mole.android.mole.component
import com.mole.android.mole.databinding.ItemDebtsTitleBinding
import com.mole.android.mole.debts.data.DebtsData
import com.mole.android.mole.summaryToString

class DebtsTitleViewHolder(private val binding: ItemDebtsTitleBinding) :
    RecyclerView.ViewHolder(binding.root), MoleBinder<DebtsData.TotalDebtsData> {
    override fun bind(data: DebtsData.TotalDebtsData) {
        binding.titleChatTextView.text = component().context.resources.getString(
            R.string.total_debts,
            summaryToString(data.debtsTotal.toLong())
        )
    }
}
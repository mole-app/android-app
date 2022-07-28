package com.mole.android.mole.debts.view.view_holder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.mole.android.mole.MoleBinder
import com.mole.android.mole.R
import com.mole.android.mole.component
import com.mole.android.mole.databinding.ItemDebtsViewBinding
import com.mole.android.mole.debts.data.DebtorData
import com.mole.android.mole.debts.view.OnItemDebtsClickListener

class DebtsDebtorViewHolder(
    private val binding: ItemDebtsViewBinding,
    private val onItemClickListener: OnItemDebtsClickListener
) :
    RecyclerView.ViewHolder(binding.root),
    MoleBinder<DebtorData> {
    override fun bind(data: DebtorData) {
        binding.personName.text = data.name
        binding.personDebtsTotal.balance = data.debtsSum
        binding.personDebtsCount.text = component().context.resources.getQuantityString(
            R.plurals.debts_plurals,
            data.debtsCount,
            data.debtsCount
        )
        binding.personIcon.load(data.imageUrl) {
            error(R.drawable.ic_not_avatar_foreground)
            transformations(CircleCropTransformation())
        }
        with(binding.itemChatView) {
            setOnClickListener {
                onItemClickListener.onShotClick(data)
            }
            setOnLongClickListener {
                onItemClickListener.onLongClick(it, data)
                true
            }
        }
    }
}
package com.mole.android.mole.debts.view.view_holder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.mole.android.mole.MoleBinder
import com.mole.android.mole.R
import com.mole.android.mole.component
import com.mole.android.mole.databinding.ItemDebtsViewBinding
import com.mole.android.mole.debts.data.DebtsData
import com.mole.android.mole.debts.view.OnItemDebtsClickListener

class DebtsDebtorViewHolder(
    private val binding: ItemDebtsViewBinding,
    private val onItemClickListener: OnItemDebtsClickListener
) :
    RecyclerView.ViewHolder(binding.root),
    MoleBinder<DebtsData.ChatDebtsData> {
    override fun bind(data: DebtsData.ChatDebtsData) {
        binding.personName.text = data.personName
        binding.personDebtsTotal.balance = data.personDebtsTotal
        binding.personDebtsCount.text = component().context.resources.getQuantityString(
            R.plurals.debts_plurals,
            data.personDebtsCount,
            data.personDebtsCount
        )
        binding.personIcon.load(data.personIcon) {
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
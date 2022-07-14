package com.mole.android.mole.debts.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.MoleBaseRecyclerAdapter
import com.mole.android.mole.databinding.ItemDebtsTitleBinding
import com.mole.android.mole.databinding.ItemDebtsViewBinding
import com.mole.android.mole.debts.data.DebtsData
import com.mole.android.mole.debts.view.view_holder.DebtsDebtorViewHolder
import com.mole.android.mole.debts.view.view_holder.DebtsTitleViewHolder

class DebtsAdapter(
    private val onItemChatClickListener: OnItemDebtsClickListener
) : MoleBaseRecyclerAdapter<DebtsData>() {

    override fun getViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(viewGroup.context)
        return when (viewType) {
            TYPE_HEADER -> {
                DebtsTitleViewHolder(
                    ItemDebtsTitleBinding.inflate(inflater, viewGroup, false)
                )
            }
            else -> {
                DebtsDebtorViewHolder(
                    ItemDebtsViewBinding.inflate(inflater, viewGroup, false),
                    onItemChatClickListener
                )
            }
        }
    }

    override fun getViewTypeOfData(position: Int): Int {
        return if (position == 0) TYPE_HEADER
        else TYPE_CHAT
    }

    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_CHAT = 2
    }
}
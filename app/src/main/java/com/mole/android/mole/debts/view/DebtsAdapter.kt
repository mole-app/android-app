package com.mole.android.mole.debts.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.MoleBaseRecyclerAdapter
import com.mole.android.mole.databinding.ItemDebtsViewBinding
import com.mole.android.mole.debts.data.DebtorData
import com.mole.android.mole.debts.data.DebtsData
import com.mole.android.mole.debts.view.view_holder.DebtsDebtorViewHolder

class DebtsAdapter(
    private val onItemChatClickListener: OnItemDebtsClickListener
) : MoleBaseRecyclerAdapter<DebtorData>() {

    override fun getViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(viewGroup.context)
        return DebtsDebtorViewHolder(
            ItemDebtsViewBinding.inflate(inflater, viewGroup, false),
            onItemChatClickListener
        )
    }

    override fun getViewTypeOfData(position: Int): Int {
        return TYPE_CHAT
    }

    companion object {
        private const val TYPE_CHAT = 1
    }
}
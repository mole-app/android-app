package com.mole.android.mole.debts.view

import androidx.recyclerview.widget.DiffUtil
import com.mole.android.mole.debts.data.DebtsData

class DebtsDiffUtilCallback(
    private val oldData: List<DebtsData>,
    private val newData: List<DebtsData>
): DiffUtil.Callback() {

    override fun getOldListSize() = oldData.size

    override fun getNewListSize() = newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }
}
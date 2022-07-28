package com.mole.android.mole.debts.view

import com.mole.android.mole.MoleBaseDiffUtilCallback
import com.mole.android.mole.debts.data.DebtorData

class DebtsDiffUtilCallback(
    private val oldList: List<DebtorData>,
    private val newList: List<DebtorData>
) : MoleBaseDiffUtilCallback<DebtorData>(oldList, newList) {
    override fun checkItemsId(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun checkContents(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}
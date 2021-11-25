package com.mole.android.mole.debts.view

import com.mole.android.mole.debts.data.DebtsData

interface DebtsView {
    fun setData(data: List<DebtsData>)
}
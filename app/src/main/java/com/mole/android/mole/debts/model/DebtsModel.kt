package com.mole.android.mole.debts.model

import com.mole.android.mole.debts.data.DebtsData

interface DebtsModel {
    fun getDebtsData(): List<DebtsData>
}
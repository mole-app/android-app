package com.mole.android.mole.debts.model

import com.mole.android.mole.debts.data.DebtsData
import com.mole.android.mole.debts.data.testData

class DebtsModelImplementation: DebtsModel
{
    override fun getDebtsData(): List<DebtsData> {
        return testData
    }
}
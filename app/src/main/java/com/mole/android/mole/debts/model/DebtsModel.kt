package com.mole.android.mole.debts.model

import com.mole.android.mole.debts.data.DebtsData
import com.mole.android.mole.web.service.State
import kotlinx.coroutines.flow.Flow

interface DebtsModel {
    suspend fun loadDebtsData(): Flow<State<DebtsData>>
}
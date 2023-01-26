package com.mole.android.mole.debts.model

import com.mole.android.mole.debts.data.DebtsData
import com.mole.android.mole.debts.data.asDomain
import com.mole.android.mole.web.service.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class DebtsModelImplementation(
    private val service: DebtsService,
    private val scope: CoroutineScope
) : DebtsModel {
    override suspend fun loadDebtsData(): Flow<State<DebtsData>> {
        return flow {
            emit(State.Loading)
            try {
                val data = service.getDebtors().asDomain()
                emit(State.Content(data))
            } catch (e: Exception) {
                emit(State.Error(e))
            }
        }
    }
}
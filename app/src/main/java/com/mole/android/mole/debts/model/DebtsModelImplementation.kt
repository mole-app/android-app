package com.mole.android.mole.debts.model

import com.mole.android.mole.debts.data.DebtsData
import com.mole.android.mole.debts.data.asDomain
import com.mole.android.mole.web.service.ApiResult
import com.mole.android.mole.web.service.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext


class DebtsModelImplementation(
    private val service: DebtsService,
    private val scope: CoroutineScope
) : DebtsModel {
    override suspend fun loadDebtsData(): ApiResult<DebtsData> {
        return withContext(scope.coroutineContext) {
            call { service.getDebtors().asDomain() }
        }
    }
}
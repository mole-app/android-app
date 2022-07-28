package com.mole.android.mole.debts.model

import com.mole.android.mole.debts.data.asDomain
import com.mole.android.mole.web.service.ApiResult
import com.mole.android.mole.web.service.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async


class DebtsModelImplementation(
    private val service: DebtsService,
    private val scope: CoroutineScope
) : DebtsModel {
    override suspend fun loadDebtsData(): ApiResult<SuccessDebtsResult> {
        val task = scope.async {
            call { service.getDebtors().asDomain() }
        }
        return task.await()
    }
}
package com.mole.android.mole.create.data

import com.mole.android.mole.create.model.asDomain
import com.mole.android.mole.web.service.ApiResult
import com.mole.android.mole.web.service.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class RemoteCreateDebtModel(
    private val createDebtService: CreateDebtService,
    private val scope: CoroutineScope
) : CreateDebtModel {

    override suspend fun createDebt(
        userId: Int,
        side: Boolean,
        tag: String,
        amount: Int
    ): ApiResult<SuccessCreateDebtResult> {
        val type = if (side) "Give" else "Get"
        val task = scope.async {
            call { createDebtService.createDebt(userId, amount, tag, type).asDomain() }
        }
        return task.await()
    }

}
package com.mole.android.mole.repay.model

import com.mole.android.mole.web.service.ApiResult
import com.mole.android.mole.web.service.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

class RepayModelImplementation(
    private val service: RepayService,
    private val scope: CoroutineScope
) : RepayModel {
    override suspend fun repayAmount(
        userId: Int,
        amount: Int,
        isUserRepayDebt: Boolean
    ): ApiResult<SuccessRepayResult> {
        return withContext(scope.coroutineContext) {
            call {
                service.repayAmount(
                    userId = userId,
                    debtType = if (isUserRepayDebt) "GET" else "GIVE",
                    sum = amount
                )
            }
        }
    }
}

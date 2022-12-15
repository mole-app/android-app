package com.mole.android.mole.repay.model

import com.mole.android.mole.web.service.ApiResult

typealias SuccessRepayResult = Unit

interface RepayModel {
    suspend fun repayAmount(
        userId: Int,
        amount: Int,
        isUserRepayDebt: Boolean
    ): ApiResult<SuccessRepayResult>
}

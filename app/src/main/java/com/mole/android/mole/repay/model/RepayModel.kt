package com.mole.android.mole.repay.model

import com.mole.android.mole.chat.data.DebtType
import com.mole.android.mole.web.service.ApiResult

typealias SuccessRepayResult = Unit

interface RepayModel {
    suspend fun repayAmount(userId: Int, type: DebtType, amount: Int): ApiResult<SuccessRepayResult>
}

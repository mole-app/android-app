package com.mole.android.mole.create.data

import com.mole.android.mole.create.model.CreatedDebt
import com.mole.android.mole.web.service.ApiResult

typealias SuccessCreateDebtResult = CreatedDebt

interface CreateDebtModel {
    suspend fun createDebt(
        userId: Int,
        side: Boolean,
        tag: String,
        amount: Int
    ): ApiResult<SuccessCreateDebtResult>
}
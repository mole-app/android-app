package com.mole.android.mole.debts.model

import com.mole.android.mole.debts.data.DebtsData
import com.mole.android.mole.web.service.ApiResult

interface DebtsModel {
    suspend fun getDebtsData(): ApiResult<SuccessDebtsResult>
    class SuccessDebtsResult(val debtsData: DebtsData)
}
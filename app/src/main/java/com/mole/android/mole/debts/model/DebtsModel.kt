package com.mole.android.mole.debts.model

import com.mole.android.mole.debts.data.DebtsData
import com.mole.android.mole.web.service.ApiResult

typealias SuccessDebtsResult = DebtsData

interface DebtsModel {
    suspend fun loadDebtsData(): ApiResult<SuccessDebtsResult>
}
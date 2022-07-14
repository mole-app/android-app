package com.mole.android.mole.debts.model

import com.mole.android.mole.debts.data.testDebtsData
import com.mole.android.mole.web.service.ApiResult
import java.lang.Thread.sleep

class MockedDebtsModel : DebtsModel {
    override suspend fun getDebtsData(): ApiResult<DebtsModel.SuccessDebtsResult> {
        sleep(1000)
        return ApiResult.create(DebtsModel.SuccessDebtsResult(testDebtsData))
    }
}
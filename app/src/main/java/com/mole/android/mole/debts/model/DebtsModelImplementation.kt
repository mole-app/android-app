package com.mole.android.mole.debts.model

import com.mole.android.mole.debts.data.testDebtsData
import com.mole.android.mole.web.service.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import retrofit2.HttpException


class DebtsModelImplementation(
    private val service: DebtsService,
    private val mainScope: CoroutineScope
) : DebtsModel {
    override suspend fun getDebtsData(): ApiResult<DebtsModel.SuccessDebtsResult> {
        val task = mainScope.async(Dispatchers.IO) {
            try {
                delay(1000)
                ApiResult.create(
                    DebtsModel.SuccessDebtsResult(
                        testDebtsData
                    )
                )

            } catch (exception: HttpException) {
                ApiResult.create(
                    ApiResult.MoleError(
                        exception.code(),
                        exception.message()
                    )
                )
            }
        }
        return task.await()
    }
}
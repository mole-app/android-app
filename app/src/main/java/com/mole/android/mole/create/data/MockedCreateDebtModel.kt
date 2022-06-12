package com.mole.android.mole.create.data

import com.mole.android.mole.create.model.CreatedDebt
import com.mole.android.mole.web.service.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlin.random.Random

class MockedCreateDebtModel(private val scope: CoroutineScope) : CreateDebtModel {
    override suspend fun createDebt(
        userId: Int,
        side: Boolean,
        tag: String,
        amount: Int
    ): ApiResult<SuccessCreateDebtResult> {
        val delay = Random.nextLong(300, 1000)
        val task = scope.async {
            delay(delay)
//        val random = Random.nextBoolean()
//            if (random) {
            ApiResult.create(CreatedDebt(1))
//            } else {
//                ApiResult.create(ApiResult.MoleError(0, "Error"))
//            }
        }
        return task.await()
    }
}
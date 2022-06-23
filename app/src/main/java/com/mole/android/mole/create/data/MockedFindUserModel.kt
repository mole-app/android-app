package com.mole.android.mole.create.data

import com.mole.android.mole.web.service.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlin.random.Random

class MockedFindUserModel(private val scope: CoroutineScope) : FindUserModel {
    override suspend fun profilePreviews(filter: String): ApiResult<SuccessPreviewsResult> {
        val delay = Random.nextLong(300, 1000)
        val task = scope.async {
            delay(delay)
            if (filter == "error") return@async ApiResult.create(ApiResult.MoleError(0, "Error"))
            val data = usersTestData
            val filtered = data.filter {
                it.name.contains(filter, true) ||
                        it.login.contains(filter, true)
            }
            ApiResult.create(filtered)
        }
        return task.await()
    }
}
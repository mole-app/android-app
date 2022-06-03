package com.mole.android.mole.create.data

import com.mole.android.mole.web.service.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import java.lang.Thread.sleep
import kotlin.random.Random

class MockedFindUserModel(private val scope: CoroutineScope) : FindUserModel {
    override suspend fun profilePreviews(filter: String): ApiResult<SuccessPreviewsResult> {
        Random.nextLong(300, 1000)
        val task = scope.async {
            sleep(1000)
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
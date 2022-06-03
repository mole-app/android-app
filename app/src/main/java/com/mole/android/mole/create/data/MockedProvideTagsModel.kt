package com.mole.android.mole.create.data

import com.mole.android.mole.web.service.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlin.random.Random

class MockedProvideTagsModel(private val scope: CoroutineScope) : ProvideTagsModel {
    override suspend fun provideTags(): ApiResult<SuccessTagsResult> {
        val delay = Random.nextLong(300, 1000)
        val task = scope.async {
            delay(delay)
            ApiResult.create(tagsTestData)
        }
        return task.await()
    }
}
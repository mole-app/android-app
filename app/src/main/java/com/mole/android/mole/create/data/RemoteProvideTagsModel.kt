package com.mole.android.mole.create.data

import com.mole.android.mole.create.model.asDomain
import com.mole.android.mole.web.service.ApiResult
import com.mole.android.mole.web.service.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class RemoteProvideTagsModel(
    private val service: CreateDebtService,
    private val scope: CoroutineScope
) : ProvideTagsModel {

    override suspend fun provideTags(): ApiResult<SuccessTagsResult> {
        val task = scope.async {
            call { service.provideTags(EMPTY_FILTER, MAX_COUNT).asDomain() }
        }
        return task.await()
    }

    companion object {
        private const val MAX_COUNT = 100
        private const val EMPTY_FILTER = ""
    }
}
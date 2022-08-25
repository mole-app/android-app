package com.mole.android.mole.create.data

import com.mole.android.mole.create.model.asDomain
import com.mole.android.mole.web.service.ApiResult
import com.mole.android.mole.web.service.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

class RemoteProvideTagsModel(
    private val service: CreateDebtService,
    private val scope: CoroutineScope
) : ProvideTagsModel {

    override suspend fun provideTags(): ApiResult<SuccessTagsResult> {
        return withContext(scope.coroutineContext) {
            call { service.provideTags(EMPTY_FILTER, MAX_COUNT).asDomain() }
        }
    }

    companion object {
        private const val MAX_COUNT = 100
        private const val EMPTY_FILTER = ""
    }
}
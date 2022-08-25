package com.mole.android.mole.create.data

import com.mole.android.mole.create.model.asDomain
import com.mole.android.mole.web.service.ApiResult
import com.mole.android.mole.web.service.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class RemoteFindUserModel(
    private val createDebtService: CreateDebtService,
    private val scope: CoroutineScope
) : FindUserModel {

    override suspend fun profilePreviews(filter: String): ApiResult<SuccessPreviewsResult> {
        return withContext(scope.coroutineContext) {
            call { createDebtService.findUsers(filter, 30).asDomain() }
        }
    }
}
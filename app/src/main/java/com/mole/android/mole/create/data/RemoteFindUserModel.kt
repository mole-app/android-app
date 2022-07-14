package com.mole.android.mole.create.data

import com.mole.android.mole.create.model.asDomain
import com.mole.android.mole.web.service.ApiResult
import com.mole.android.mole.web.service.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class RemoteFindUserModel(
    private val createDebtService: CreateDebtService,
    private val scope: CoroutineScope
) : FindUserModel {

    override suspend fun profilePreviews(filter: String): ApiResult<SuccessPreviewsResult> {
        val task = scope.async {
            call { createDebtService.findUsers(filter, 30).asDomain() }
        }
        return task.await()
    }
}
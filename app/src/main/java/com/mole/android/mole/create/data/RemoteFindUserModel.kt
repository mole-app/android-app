package com.mole.android.mole.create.data

import com.mole.android.mole.create.model.asDomain
import com.mole.android.mole.web.service.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import retrofit2.HttpException

class RemoteFindUserModel(
    private val findUserService: FindUserService,
    private val scope: CoroutineScope
) : FindUserModel {

    override suspend fun profilePreviews(filter: String): ApiResult<SuccessPreviewsResult> {
        val task = scope.async {
            try {
                val usersPreview = findUserService.findUsers(filter, 30)
                ApiResult.create(usersPreview.asDomain())
            } catch (exception: Exception) {
                ApiResult.create(
                    ApiResult.MoleError(
                        0,
                        ""
                    )
                )
            }
        }
        return task.await()
    }
}
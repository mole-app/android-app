package com.mole.android.mole.create.data

import com.mole.android.mole.create.model.UserPreview
import com.mole.android.mole.web.service.ApiResult

typealias SuccessPreviewsResult = List<UserPreview>

interface FindUserModel {
    suspend fun profilePreviews(filter: String = ""): ApiResult<SuccessPreviewsResult>
}
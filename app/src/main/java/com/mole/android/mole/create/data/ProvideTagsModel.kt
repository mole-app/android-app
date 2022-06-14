package com.mole.android.mole.create.data

import com.mole.android.mole.create.model.TagPreview
import com.mole.android.mole.web.service.ApiResult

typealias SuccessTagsResult = List<TagPreview>

interface ProvideTagsModel {
    suspend fun provideTags(): ApiResult<SuccessTagsResult>
}
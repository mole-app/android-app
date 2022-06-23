package com.mole.android.mole.create.data

import com.mole.android.mole.create.model.UserPreviewsRemote
import retrofit2.http.GET
import retrofit2.http.Query

interface CreateDebtService {
    @GET("debt/usersSearch")
    suspend fun findUsers(
        @Query("filter") filter: String,
        @Query("limit") limit: Int
    ): UserPreviewsRemote
}
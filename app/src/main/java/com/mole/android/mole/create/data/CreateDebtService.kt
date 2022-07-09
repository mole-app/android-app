package com.mole.android.mole.create.data

import com.mole.android.mole.create.model.CreatedDebtRemote
import com.mole.android.mole.create.model.TagsPreviewRemote
import com.mole.android.mole.create.model.UserPreviewsRemote
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface CreateDebtService {
    @GET("debt/usersSearch")
    suspend fun findUsers(
        @Query("filter") filter: String,
        @Query("limit") limit: Int
    ): UserPreviewsRemote

    @PUT("debt/createDebt")
    suspend fun createDebt(
        @Query("idUser") id: Int,
        @Query("sum") sum: Int,
        @Query("tag") tag: String,
        @Query("debtType") type: String
    ): CreatedDebtRemote

    @GET("debt/tagsSearch")
    suspend fun provideTags(
        @Query("filter") filter: String,
        @Query("limit") limit: Int
    ): TagsPreviewRemote
}
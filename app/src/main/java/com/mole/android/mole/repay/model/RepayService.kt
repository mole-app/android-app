package com.mole.android.mole.repay.model

import retrofit2.http.PUT
import retrofit2.http.Query

interface RepayService {

    @PUT("debt/createRepay")
    suspend fun repayAmount(
        @Query("sum") sum: Int,
        @Query("idUser") userId: Int,
        @Query("debtType") debtType: String )
}

package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.ChatDataDomain
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatService {
    @GET("debt/debts")
    suspend fun getChatData(
        @Query("idUser")
        idUser: Int,
        @Query("limit")
        limit: Int
    ): ChatDataDomain

    @GET("debt/debts")
    suspend fun getChatDataBeforeIdDebtMax(
        @Query("idUser")
        idUser: Int,
        @Query("idDebtMax")
        idDebtMax: Int,
        @Query("limit")
        limit: Int
    ): ChatDataDomain
}
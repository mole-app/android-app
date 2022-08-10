package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.ChatDataRemote
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatService {
    @GET("debt/debts")
    suspend fun getChatData(
        @Query("idUser")
        idUser: Int,
        @Query("limit")
        limit: Int
    ): ChatDataRemote

    @GET("debt/debts")
    suspend fun getChatDataBeforeIdDebtMax(
        @Query("idUser")
        idUser: Int,
        @Query("limit")
        limit: Int,
        @Query("idDebtMax")
        idDebtMax: Int
    ): ChatDataRemote
}
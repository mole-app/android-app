package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.ChatDataDomain
import retrofit2.http.GET

interface ChatService {
    @GET("dept/debts")
    suspend fun getChatData(idUser: Int, idDebtMax: Int = 0): ChatDataDomain
}
package com.mole.android.mole.debts.model

import com.mole.android.mole.debts.data.DebtsDataRemote
import retrofit2.http.GET

interface DebtsService {

    @GET("debt/debtors")
    suspend fun getDebtors(): DebtsDataRemote

}
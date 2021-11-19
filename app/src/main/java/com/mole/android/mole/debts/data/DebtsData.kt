package com.mole.android.mole.debts.data

sealed class DebtsData{
    data class TotalDebtsData(
        val debtsTotal: Int
    ): DebtsData()
    data class ChatDebtsData(
        val personName: String,
        val personDebtsCount: Int,
        val personIcon: String,
        val personDebtsTotal: Int
    ): DebtsData()
}

val testData = listOf<DebtsData>(
    DebtsData.TotalDebtsData(+12000),
    DebtsData.ChatDebtsData( "Александр", 10, "1", +1500),
    DebtsData.ChatDebtsData("Андрей", 1, "2", -500),
    DebtsData.ChatDebtsData( "Анастасия", 2, "3", +100),
)

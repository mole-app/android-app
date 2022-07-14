package com.mole.android.mole.debts.data

sealed class DebtsData{
    data class TotalDebtsData(
        val id: Int,
        val debtsTotal: Int
    ): DebtsData()
    data class ChatDebtsData(
        val id: Int,
        val personName: String,
        val personDebtsCount: Int,
        val personIcon: String,
        val personDebtsTotal: Int
    ): DebtsData()
}

val testDebtsData = listOf<DebtsData>(
    DebtsData.TotalDebtsData(0, +12000),
    DebtsData.ChatDebtsData( 1, "Александр", 10, "1", +1500),
    DebtsData.ChatDebtsData(2, "Андрей", 1, "2", -500),
    DebtsData.ChatDebtsData( 3, "Анастасия", 2, "3", +100),
)

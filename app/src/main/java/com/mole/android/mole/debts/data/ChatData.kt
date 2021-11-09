package com.mole.android.mole.debts.data

data class ChatData(
    val userIcon : String,
    val userName : String,
    val userDebtsCount : String,
    val userTotalDebts : Int
)

val testData = listOf<ChatData>(
    ChatData("0", "Test", "Test", +0),
    ChatData("1", "Александр", "10 долгов", +1500),
    ChatData("2", "Андрей", "1 долг", -500),
    ChatData("3", "Анастасия", "2 долга", +100),
)



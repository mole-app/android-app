package com.mole.android.mole.debts.data

data class DebtsData(
    val debtsSumTotal: Int,
    val debtors: List<DebtorData>
)

val testDebtsData = DebtsData(
    debtsSumTotal = +12000,
    debtors = listOf(
        DebtorData(1, "Александр", 10, +1500, "1"),
        DebtorData(2, "Андрей", 1, -500, "2"),
        DebtorData(3, "Анастасия", 2, +100, "3"),
        DebtorData(1, "Александр", 10, +1500, "1"),
        DebtorData(2, "Андрей", 1, -500, "2"),
        DebtorData(3, "Анастасия", 2, +100, "3"),
        DebtorData(1, "Александр", 10, +1500, "1"),
        DebtorData(2, "Андрей", 1, -500, "2"),
        DebtorData(3, "Анастасия", 2, +100, "3"),
        DebtorData(1, "Александр", 10, +1500, "1"),
        DebtorData(2, "Андрей", 1, -500, "2"),
        DebtorData(3, "Анастасия", 2, +100, "3"),
        DebtorData(1, "Александр", 10, +1500, "1"),
        DebtorData(2, "Андрей", 1, -500, "2"),
        DebtorData(3, "Анастасия", 2, +100, "3"),
    )
)


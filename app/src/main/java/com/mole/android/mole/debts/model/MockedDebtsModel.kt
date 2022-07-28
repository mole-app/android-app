package com.mole.android.mole.debts.model

import com.mole.android.mole.debts.data.DebtorData
import com.mole.android.mole.debts.data.DebtsData
import com.mole.android.mole.web.service.ApiResult
import com.mole.android.mole.web.service.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class MockedDebtsModel(
    private val scope: CoroutineScope
) : DebtsModel {
    override suspend fun loadDebtsData(): ApiResult<SuccessDebtsResult> {
        val task = scope.async {
            call {
                delay(1000)
                testDebtsData
            }
        }
        return task.await()
    }
}

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
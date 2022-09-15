package com.mole.android.mole.debts.model

import com.mole.android.mole.debts.data.DebtorData
import com.mole.android.mole.debts.data.DebtsData
import com.mole.android.mole.web.service.ApiResult
import com.mole.android.mole.web.service.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MockedDebtsModel(
    private val scope: CoroutineScope
) : DebtsModel {
    override suspend fun loadDebtsData(): ApiResult<SuccessDebtsResult> {
        return withContext(scope.coroutineContext) {
            call {
                delay(1000)
                testDebtsData
            }
        }
    }
}

val testDebtsData = DebtsData(
    debtsSumTotal = +1700,
    debtors = listOf(
        DebtorData(44, "Ярослав", 5, +600, "https://sun9-46.userapi.com/s/v1/ig2/wrJpaM_B31hRwzRRyBVZVKHjlFMJZYzX2pIYBI63qKxS1TnYgdsypnlzPn8BMKrKE7cKpWSz4_gycFr8NS_tHmZ0.jpg?size=200x200&quality=96&crop=1,272,954,954&ava=1"),
        DebtorData(1, "Егор", 2, 0, "https://sun9-85.userapi.com/s/v1/if1/yDA3MTIAl116b3M2n4VhFRecBg2ibyPX7IgYOsxkonapVtFsmrIk-GV1pCN9edKQLvqQxzgj.jpg?size=400x400&quality=96&crop=0,0,1280,1280&ava=1"),
        DebtorData(1, "Андрей", 3, -500, "https://sun9-44.userapi.com/s/v1/ig2/n97DP_GEDreatTFL4P7gRbVoQ2WeEkMEFD_0QQ4pRZJST9lxqugtINWRCOmj5FGgQWLJlAABtQxQpgrQ3Z0hBksR.jpg?size=200x200&quality=95&crop=419,364,852,852&ava=1"),
        DebtorData(1, "Оля", 7, +1500, "https://sun9-19.userapi.com/s/v1/ig2/_5JhzPZlVr8D4bKmkRSgab7P1nwGtJvjP_6yWmrvjcqtakalq95zW6kyOye8wsFUPWZEU8SD79fm01_ZsVAb6xJx.jpg?size=368x368&quality=95&crop=182,15,368,368&ava=1"),
        DebtorData(1, "Виктория", 1, -100, "https://sun9-66.userapi.com/s/v1/ig2/4NmOtMOKSEotibbWfZMVX7WQTD4to_7nzsIVDMjgXkwoFb--Hq_7qdAQra-xy29B1EAAjD96jvLW4ip0PTj4M3i8.jpg?size=400x400&quality=95&crop=0,372,594,594&ava=1"),
//        DebtorData(1, "Александр", 10, +1500, "1"),
//        DebtorData(2, "Андрей", 1, -500, "2"),
//        DebtorData(3, "Анастасия", 2, +100, "3"),
//        DebtorData(1, "Александр", 10, +1500, "1"),
//        DebtorData(2, "Андрей", 1, -500, "2"),
//        DebtorData(3, "Анастасия", 2, +100, "3"),
//        DebtorData(1, "Александр", 10, +1500, "1"),
//        DebtorData(2, "Андрей", 1, -500, "2"),
//        DebtorData(3, "Анастасия", 2, +100, "3"),
//        DebtorData(1, "Александр", 10, +1500, "1"),
//        DebtorData(2, "Андрей", 1, -500, "2"),
//        DebtorData(3, "Анастасия", 2, +100, "3"),
//        DebtorData(1, "Александр", 10, +1500, "1"),
//        DebtorData(2, "Андрей", 1, -500, "2"),
//        DebtorData(3, "Анастасия", 2, +100, "3"),
    )
)
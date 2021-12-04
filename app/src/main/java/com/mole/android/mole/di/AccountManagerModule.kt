package com.mole.android.mole.di

import android.content.Context
import com.mole.android.mole.component
import com.mole.android.mole.web.service.AccountRepository

class AccountManagerModule(context: Context) {
    val accountRepository = AccountRepository(context, component().activity)
}

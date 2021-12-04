package com.mole.android.mole.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.mole.android.mole.web.service.AccountRepository

class AccountManagerModule(context: Context, activity: AppCompatActivity?) {
    val accountRepository = AccountRepository(context, activity)
}

package com.mole.android.mole.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class BaseScopeModule {
    val mainScope by lazy { CoroutineScope(Job() + Dispatchers.Main) }
    val ioScope by lazy { CoroutineScope(Dispatchers.IO) }
}
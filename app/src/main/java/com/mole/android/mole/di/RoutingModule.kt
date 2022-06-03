package com.mole.android.mole.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mole.android.mole.R

class RoutingModule {

    val router get() = cicerone.router

    val navigationHolder get() = cicerone.getNavigatorHolder()

//    val navigator by lazy { AppNavigator(this, R.id.fragment_container) }

    private val cicerone by lazy { Cicerone.create() }

}
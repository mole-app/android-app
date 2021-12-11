package com.mole.android.mole.di

import com.github.terrakok.cicerone.Cicerone

class RoutingModule {

    val router get() = cicerone.router

    val navigationHolder get() = cicerone.getNavigatorHolder()

    private val cicerone by lazy { Cicerone.create() }

}
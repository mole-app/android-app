package com.mole.android.mole.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.mole.android.mole.auth.view.AuthBeginViewImplementation
import com.mole.android.mole.auth.view.AuthLoginViewImplementation
import com.mole.android.mole.auth.view.AuthWebViewImpl
import com.mole.android.mole.bottomNavigation.view.BottomBarViewImpl
import com.mole.android.mole.chat.view.ChatViewImplementation
import com.mole.android.mole.create.view.CreateDebtScreen
import com.mole.android.mole.devpanel.view.MoleDebugPanelViewImpl
import com.mole.android.mole.test.FragmentBottomBarTest

object Screens {
    fun AuthLogin(login: String) = FragmentScreen { AuthLoginViewImplementation.newInstance(login) }

    fun AuthBrowser(url: String) = FragmentScreen { AuthWebViewImpl.newInstance(url) }

    fun DevPanel() = FragmentScreen { MoleDebugPanelViewImpl() }

    fun AuthBegin() = FragmentScreen { AuthBeginViewImplementation() }

    fun TestScreen() = FragmentScreen { FragmentBottomBarTest() }

    fun Debts() = FragmentScreen { BottomBarViewImpl.withDebts() }

    fun Profile() = FragmentScreen { BottomBarViewImpl.withProfile() }

    fun Chat(userId: Int) =
        FragmentScreen { ChatViewImplementation.newInstance(userId) }
}
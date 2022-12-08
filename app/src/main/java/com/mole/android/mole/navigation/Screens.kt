package com.mole.android.mole.navigation

import android.content.Intent
import android.net.Uri
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.mole.android.mole.about.view.AboutViewImpl
import com.mole.android.mole.about.view.NameAboutViewImpl
import com.mole.android.mole.auth.view.AuthBeginViewImplementation
import com.mole.android.mole.auth.view.AuthLoginViewImplementation
import com.mole.android.mole.auth.view.AuthWebViewImpl
import com.mole.android.mole.bottomNavigation.view.BottomBarViewImpl
import com.mole.android.mole.chat.view.ChatViewImplementation
import com.mole.android.mole.component
import com.mole.android.mole.devpanel.view.MoleDebugPanelViewImpl
import com.mole.android.mole.repay.data.RepayData
import com.mole.android.mole.repay.view.RepayViewImplementation
import com.mole.android.mole.settings.view.SettingsViewImpl
import com.mole.android.mole.test.TestScreenFragment

object Screens {
    fun AuthLogin(login: String) = FragmentScreen { AuthLoginViewImplementation.newInstance(login) }

    fun AuthBrowser(url: String) = FragmentScreen { AuthWebViewImpl.newInstance(url) }

    fun DevPanel() = FragmentScreen { MoleDebugPanelViewImpl() }

    fun AuthBegin() = FragmentScreen { AuthBeginViewImplementation() }

    fun About() = FragmentScreen { AboutViewImpl() }

    fun NameAbout() = FragmentScreen { NameAboutViewImpl() }

    fun Codehub() = ActivityScreen {
        Intent(Intent.ACTION_VIEW, Uri.parse(component().buildConfigModule.PUBLIC_CODE_SOURCE))
    }

    fun Settings() = FragmentScreen { SettingsViewImpl() }

    fun TestScreen() = FragmentScreen { TestScreenFragment() }

    fun Debts() = FragmentScreen { BottomBarViewImpl.withDebts() }

    fun Profile() = FragmentScreen { BottomBarViewImpl.withProfile() }

    fun Chat(userId: Int) =
        FragmentScreen { ChatViewImplementation.newInstance(userId) }

    fun Repay(repayData: RepayData, openChat: Boolean = false) =
        FragmentScreen { RepayViewImplementation.newInstance(repayData, openChat) }
}
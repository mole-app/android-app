package com.mole.android.mole.settings.view

import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.component
import com.mole.android.mole.databinding.ViewSettingsBinding
import com.mole.android.mole.navigation.Screens.About
import com.mole.android.mole.settings.presentation.SettingsPresenter
import com.mole.android.mole.ui.actionbar.MoleActionBar

class SettingsViewImpl :
    MoleBaseFragment<ViewSettingsBinding>(ViewSettingsBinding::inflate), SettingsView {

    private lateinit var presenter: SettingsPresenter
    private val routing = component().routingModule.router
    private val accountRepository = component().accountManagerModule.accountRepository


    override fun getToolbar(): MoleActionBar {
        return binding.moleSettingsToolbar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = component().settingsModule.settingsPresenter

        binding.aboutGroup.setOnClickListener {
            routing.navigateTo(About())
        }

        binding.exitGroup.setOnClickListener {
            CookieManager.getInstance().removeAllCookies(null)
            CookieManager.getInstance().flush()
            accountRepository.removeAllAccount { }
        }
    }
}

package com.mole.android.mole.settings.view

import android.os.Bundle
import android.view.View
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.component
import com.mole.android.mole.databinding.ViewSettingsBinding
import com.mole.android.mole.navigation.Screens.About
import com.mole.android.mole.settings.presentation.SettingsPresenter
import com.mole.android.mole.ui.actionbar.MoleActionBar

class SettingsViewImpl : MoleBaseFragment<ViewSettingsBinding>(ViewSettingsBinding::inflate), SettingsView {

    private lateinit var presenter: SettingsPresenter
    private val routing = component().routingModule.router


    override fun getToolbar(): MoleActionBar {
        return binding.moleSettingsToolbar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = component().settingsModule.settingsPresenter

        binding.aboutGroup.setOnClickListener {
            routing.navigateTo(About())
        }
    }

    override fun exitAccount() {
        TODO("Not yet implemented")
    }

}
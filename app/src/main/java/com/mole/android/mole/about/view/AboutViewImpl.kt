package com.mole.android.mole.about.view

import android.os.Bundle
import android.view.View
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.R
import com.mole.android.mole.component
import com.mole.android.mole.databinding.ViewAboutBinding
import com.mole.android.mole.navigation.Screens.Codehub
import com.mole.android.mole.ui.actionbar.MoleActionBar

class AboutViewImpl : MoleBaseFragment<ViewAboutBinding>(ViewAboutBinding::inflate) {

    val router = component().routingModule.router
    private val appVersion = component().buildConfigModule.APP_VERSION

    override fun getToolbar(): MoleActionBar {
        return binding.moleAboutToolbar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.codeSourceGroup.setOnClickListener {
            router.navigateTo(Codehub())
        }
        binding.versionUnderline.text = requireContext().getString(R.string.settings_version_underline, appVersion)
    }
}
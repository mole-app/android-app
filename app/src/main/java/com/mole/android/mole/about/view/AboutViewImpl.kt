package com.mole.android.mole.about.view

import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.databinding.ViewAboutBinding
import com.mole.android.mole.ui.actionbar.MoleActionBar

class AboutViewImpl : MoleBaseFragment<ViewAboutBinding>(ViewAboutBinding::inflate) {
    override fun getToolbar(): MoleActionBar {
        return binding.moleAboutToolbar
    }
}
package com.mole.android.mole.about.view

import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.databinding.ViewNameAboutBinding
import com.mole.android.mole.ui.actionbar.MoleActionBar

class NameAboutViewImpl  : MoleBaseFragment<ViewNameAboutBinding>(ViewNameAboutBinding::inflate) {

    override fun getToolbar(): MoleActionBar {
        return binding.moleNameAboutToolbar
    }

}
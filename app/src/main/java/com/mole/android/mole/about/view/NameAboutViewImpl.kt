package com.mole.android.mole.about.view

import android.os.Bundle
import android.view.View
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.databinding.ViewNameAboutBinding
import com.mole.android.mole.dp
import com.mole.android.mole.setupCornerRadius
import com.mole.android.mole.ui.actionbar.MoleActionBar

class NameAboutViewImpl : MoleBaseFragment<ViewNameAboutBinding>(ViewNameAboutBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageNameAbout.setupCornerRadius(12f.dp)
    }

    override fun getToolbar(): MoleActionBar {
        return binding.moleNameAboutToolbar
    }

}
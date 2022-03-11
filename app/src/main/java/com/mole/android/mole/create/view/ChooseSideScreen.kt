package com.mole.android.mole.create.view

import android.os.Bundle
import android.view.View
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.databinding.FragmentChooseSideBinding

class ChooseSideScreen : MoleBaseFragment<FragmentChooseSideBinding>(FragmentChooseSideBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createDebtPositiveButton.render(ChooseSideCardView.Side.HIS_DEBT)
        binding.createDebtNegativeButton.render(ChooseSideCardView.Side.MY_DEBT)
    }

}
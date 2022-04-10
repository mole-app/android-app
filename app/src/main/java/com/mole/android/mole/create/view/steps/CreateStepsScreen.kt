package com.mole.android.mole.create.view.steps

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.google.android.material.tabs.TabLayoutMediator
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.databinding.FragmentCreateStepsBinding
import com.mole.android.mole.ui.actionbar.MoleActionBar

class CreateStepsScreen :
    MoleBaseFragment<FragmentCreateStepsBinding>(FragmentCreateStepsBinding::inflate) {

    override fun getSoftMode(): Int {
        return WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
    }

    override fun getToolbar(): MoleActionBar {
        return binding.actionBar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = StepsAdapter(
            listOf(Steps.ChooseName, Steps.ChooseTag, Steps.ChooseAmount),
        ) {
            binding.viewPager.setCurrentItem(it + 1, true)
        }
        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
        }.attach()
        adapter.notifyDataSetChanged()
    }
}
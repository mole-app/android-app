package com.mole.android.mole.create.view.steps

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.component
import com.mole.android.mole.databinding.FragmentCreateStepsBinding
import com.mole.android.mole.ui.actionbar.MoleActionBar

class CreateStepsScreen :
    MoleBaseFragment<FragmentCreateStepsBinding>(FragmentCreateStepsBinding::inflate) {

    private val chooseNamePresenter = component().createDebtsModule.chooseNamePresenter
    private val chooseTagPresenter = component().createDebtsModule.chooseTagPresenter
    private val chooseAmountPresenter = component().createDebtsModule.chooseAmountPresenter

    override fun getSoftMode(): Int {
        return WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
    }

    override fun getToolbar(): MoleActionBar {
        return binding.actionBar
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = StepsAdapter(
            listOf(Steps.ChooseName, Steps.ChooseTag, Steps.ChooseAmount),
            scope,
            chooseNamePresenter,
            chooseTagPresenter,
            chooseAmountPresenter
        ) {
            binding.viewPager.setCurrentItem(it + 1, true)
        }
        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.offscreenPageLimit = 1
        binding.tabLayout.touchables.forEach { it.isClickable = false }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position -> }.attach()
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        chooseNamePresenter.detachView()
        chooseTagPresenter.detachView()
        chooseAmountPresenter.detachView()
    }

    class NonTouchableTabLayout : TabLayout {

        constructor(context: Context) : super(context)
        constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

        override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
            return true
        }
    }
}
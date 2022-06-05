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
import com.mole.android.mole.create.data.CreateDebtsDataRepository
import com.mole.android.mole.databinding.FragmentCreateStepsBinding
import com.mole.android.mole.ui.actionbar.MoleActionBar

class CreateStepsScreen :
    MoleBaseFragment<FragmentCreateStepsBinding>(FragmentCreateStepsBinding::inflate) {

    private var userId: Int = 0
    private var side: Boolean = false
    private var debtTag: String = ""

    private val chooseNamePresenter = component().createDebtsModule.chooseNamePresenter
    private val chooseTagPresenter = component().createDebtsModule.chooseTagPresenter
    private val chooseAmountPresenter = component().createDebtsModule.chooseAmountPresenter(
        object : CreateDebtsDataRepository {
            override fun userId() = userId
            override fun tag() = debtTag
            override fun side() = side
        }
    )

    override fun getSoftMode(): Int {
        return WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
    }

    override fun getToolbar(): MoleActionBar {
        return binding.actionBar
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt(EXTRA_ID, -1) ?: -1
        if (id >= 0) {
            userId = id
        }
        val adapter = StepsAdapter(
            if (id < 0) {
                listOf(Steps.ChooseName, Steps.ChooseTag, Steps.ChooseAmount)
            } else {
                listOf(Steps.ChooseTag, Steps.ChooseAmount)
            },
            scope,
            chooseNamePresenter,
            chooseTagPresenter,
            chooseAmountPresenter
        ) { ix, result ->
            when(result) {
                is StepsAdapter.StepResult.TagResult -> debtTag = result.tag
                is StepsAdapter.StepResult.UserResult -> userId = result.id
            }
            binding.viewPager.setCurrentItem(ix + 1, true)
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

    companion object {
        fun instance(side: Boolean, id: Int): CreateStepsScreen {
            return CreateStepsScreen().apply {
                arguments = Bundle().apply {
                    putBoolean(EXTRA_SIDE, side)
                    putInt(EXTRA_ID, id)
                }
            }
        }

        private const val EXTRA_SIDE = "create_steps_screen_extra_side"
        private const val EXTRA_ID = "create_steps_screen_extra_id"
    }
}
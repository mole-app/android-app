package com.mole.android.mole.repay.view

import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.SeekBar
import androidx.core.widget.addTextChangedListener
import coil.load
import coil.transform.CircleCropTransformation
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mole.android.mole.*
import com.mole.android.mole.databinding.FragmetRepayBinding
import com.mole.android.mole.navigation.Screens
import com.mole.android.mole.repay.data.RepayData
import com.mole.android.mole.ui.actionbar.MoleActionBar

class RepayViewImplementation : RepayView,
    MoleBaseFragment<FragmetRepayBinding>(FragmetRepayBinding::inflate) {

    companion object {
        private const val ANIM_DURATION_PROGRESS: Long = 300
        private const val ARG_REPAY = "arg_repay"
        private const val ARG_OPEN_CHAT = "open_chat"
        fun newInstance(repayData: RepayData, openChat: Boolean = false): RepayViewImplementation {
            val args = Bundle()
            val fragment = RepayViewImplementation()
            args.putParcelable(ARG_REPAY, repayData)
            args.putBoolean(ARG_OPEN_CHAT, openChat)
            fragment.arguments = args
            return fragment
        }
    }

    override fun getSoftMode(): Int {
        return WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
    }

    private val router = component().routingModule.router

    private val presenter by lazy {
        val repayData = arguments?.getParcelable<RepayData>(ARG_REPAY)
        val openChat = arguments?.getBoolean(ARG_OPEN_CHAT, false) ?: false
        component().repayModule.repayPresenter(repayData, openChat)
    }

    private var isChangedProgrammatically: Boolean = true

    private val seekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            if (fromUser) {
                isChangedProgrammatically = true
                binding.repayEditText.text = progress.toEditable()
                binding.repayEditText.setSelection(progress.toEditable().length)
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
        override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
    }


    override fun getNavigator(): Navigator {
        return AppNavigator(requireActivity(), R.id.fragment_container)
    }

    override fun getToolbar(): MoleActionBar {
        return binding.actionBar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        presenter.onInitUiData()
        val maxValue = presenter.onInitMaxValue()
        binding.seekBarMinValue.text = requireContext()
            .getString(R.string.text_with_ruble_suffix, "0")
        binding.seekBarMaxValue.text = requireContext()
            .getString(R.string.text_with_ruble_suffix, "$maxValue")


        initEditText(maxValue)
        initSeekBar(maxValue)
        initTextField()
        initRepayButton()
        initRetryButton()
        showKeyboard()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    private fun initEditText(maxValue: Int) {
        with(binding.repayEditText) {
            addTextChangedListener { text ->
                Log.d("edit", text.toString())
                val number = provideNumber(text)

                if (number <= maxValue) {
                    provideTextToField(number)
                    provideEnabledToButton(number)

                    if (!isChangedProgrammatically) provideTextToToSeekbar(number)
                    else isChangedProgrammatically = false
                } else {
                    this.text = maxValue.toEditable()
                    this.setSelection(maxValue.toEditable().length)
                }
            }
        }
    }

    private fun initSeekBar(maxValue: Int) {
        with(binding.repaySeekBar) {
            max = maxValue
            setOnSeekBarChangeListener(seekBarChangeListener)
        }
    }

    private fun initTextField() {
        with(binding.repayText) {
            setOnClickListener { showKeyboard() }
            provideTextToField(0)
        }
    }

    private fun initRepayButton() {
        with(binding.repayBtn) {
            setOnClickListener {
                if (isEnabled) {
                    val number = binding.repayEditText.text.toString().toIntOrNull() ?: 0
                    presenter.onRepayButtonClick(number)
                }
            }
        }
    }

    private fun initRetryButton() {
        binding.retryButton.setOnClickListener {
            showContent()
            presenter.onRetryButtonClick()
            binding.retryButton.isEnabled = false
        }
        binding.retryButton.setupBorder(Shape.RECTANGLE, 80f.dp)
    }

    private fun provideTextToToSeekbar(number: Int) {
        animateProgress(binding.repaySeekBar.progress, number)
    }

    private fun provideEnabledToButton(number: Int) {
        binding.repayBtn.isEnabled = number > 0
    }

    private fun provideTextToField(number: Int) {
        binding.repayText.text = requireContext()
            .getString(R.string.text_with_ruble_suffix, number.toString())
        setHighLightedText(number)
    }

    private fun provideNumber(text: Editable?): Int {
        val string = text.toString()
        val number = string.toIntOrNull(10)
        return if (string.isBlank() || number == null || number <= 0) 0 else number
    }

    private fun setHighLightedText(number: Int) {
        if (number == 0) {
            binding.repayText.setHighLightedText(
                "0",
                color = requireContext().getColor(R.color.white_alpha_50)
            )
        }
    }

    private fun showKeyboard() {
        with(binding.repayEditText) {
            requestFocus()
            openKeyboard()
        }
    }

    private fun hideContent() {
        with(binding) {
            repayContent.visibility = View.GONE
        }
    }

    private fun showContent() {
        with(binding) {
            repayContent.visibility = View.VISIBLE
            errorContainer.visibility = View.GONE
        }
    }

    private fun animateProgress(startProgress: Int, finishProgress: Int) {
        val animation = ObjectAnimator.ofInt(
            binding.repaySeekBar,
            "progress",
            startProgress,
            finishProgress
        )

        animation.duration = ANIM_DURATION_PROGRESS
        animation.interpolator = DecelerateInterpolator()
        animation.start()
        binding.repaySeekBar.clearAnimation()
    }

    override fun initUiData(
        repayingDebtUserName: String,
        acceptorDebtUserName: String,
        repayingDebtUserIconUrl: String,
        acceptorDebtUserIconUrl: String
    ) {
        binding.repayingDebtUserName.text = repayingDebtUserName.ifBlank { getString(R.string.user_fallback) }
        binding.repayingDebtUserIcon.load(repayingDebtUserIconUrl) {
            error(R.drawable.ic_not_avatar_foreground)
            transformations(CircleCropTransformation())
        }

        binding.acceptorDebtUserName.text = acceptorDebtUserName.ifBlank { getString(R.string.user_fallback) }
        binding.acceptorDebtUserIcon.load(acceptorDebtUserIconUrl) {
            error(R.drawable.ic_not_avatar_foreground)
            transformations(CircleCropTransformation())
        }
    }

    override fun showLoading() {
        binding.loading.visibility = View.VISIBLE
        binding.repayBtn.isEnabled = false
        binding.repaySeekBar.visibility = View.INVISIBLE
        binding.seekBarMaxValue.visibility = View.VISIBLE
        binding.seekBarMinValue.visibility = View.VISIBLE
        binding.repayEditText.visibility = View.GONE
    }

    override fun hideLoading() {
        binding.loading.visibility = View.GONE
        binding.repayBtn.isEnabled = true
        binding.repaySeekBar.visibility = View.VISIBLE
        binding.seekBarMaxValue.visibility = View.GONE
        binding.seekBarMinValue.visibility = View.GONE
        binding.repayEditText.visibility = View.VISIBLE
    }

    override fun showError() {
        hideContent()
        binding.retryButton.isEnabled = true
        binding.errorContainer.visibility = View.VISIBLE
    }

    override fun closeScreen(userId: Int, isOpenChat: Boolean) {
        showContent()
        if (isOpenChat) {
            router.backTo(Screens.Chat(userId))
        } else {
            router.replaceScreen(Screens.Chat(userId))
        }
    }
}

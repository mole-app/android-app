package com.mole.android.mole.repay.view

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.core.widget.addTextChangedListener
import coil.load
import coil.transform.CircleCropTransformation
import com.mole.android.mole.*
import com.mole.android.mole.create.view.CreateDebtScreen
import com.mole.android.mole.databinding.FragmetRepayBinding
import com.mole.android.mole.repay.data.RepayData

class RepayViewImplementation : RepayView,
    MoleBaseFragment<FragmetRepayBinding>(FragmetRepayBinding::inflate) {

    companion object {
        private const val ARG_REPAY = "arg_repay"
        fun newInstance(repayData: RepayData): RepayViewImplementation {
            val args = Bundle()
            val fragment = RepayViewImplementation()
            args.putParcelable(ARG_REPAY, repayData)
            fragment.arguments = args
            return fragment
        }
    }

    private val router = component().routingModule.router

    private val presenter by lazy {
        val repayData = arguments?.getParcelable<RepayData>(ARG_REPAY)
        component().repayModule.repayPresenter(repayData)
    }

    private var isChangedProgrammatically: Boolean = true

    private val seekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            isChangedProgrammatically = true
            binding.repayEditText.text = progress.toEditable()
            binding.repayEditText.setSelection(progress.toEditable().length)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
        override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        presenter.onInitUiData()
        val maxValue = presenter.onInitMaxValue()
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
    }

    private fun provideTextToToSeekbar(number: Int) {
        binding.repaySeekBar.progress = number
        binding.repaySeekBar.refreshDrawableState()
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
            repayingDebtUserName.visibility = View.GONE
            repayingDebtUserIcon.visibility = View.GONE
            acceptorDebtUserName.visibility = View.GONE
            acceptorDebtUserIcon.visibility = View.GONE
            arrowRepayTo.visibility = View.GONE
            repayTitle.visibility = View.GONE
            repayText.visibility = View.GONE
            repaySeekBar.visibility = View.GONE
            repayBtn.visibility = View.GONE
        }
    }

    private fun showContent() {
        with(binding) {
            repayingDebtUserName.visibility = View.VISIBLE
            repayingDebtUserIcon.visibility = View.VISIBLE
            acceptorDebtUserName.visibility = View.VISIBLE
            acceptorDebtUserIcon.visibility = View.VISIBLE
            arrowRepayTo.visibility = View.VISIBLE
            repayTitle.visibility = View.VISIBLE
            repayText.visibility = View.VISIBLE
            repaySeekBar.visibility = View.VISIBLE
            repayBtn.visibility = View.VISIBLE
            repayBtn.isEnabled = true
            retryButton.visibility = View.GONE
            binding.errorContainer.visibility = View.GONE
        }
    }

    override fun initUiData(
        repayingDebtUserName: String,
        acceptorDebtUserName: String,
        repayingDebtUserIconUrl: String,
        acceptorDebtUserIconUrl: String
    ) {
        binding.repayingDebtUserName.text = repayingDebtUserName
        binding.repayingDebtUserIcon.load(repayingDebtUserIconUrl) {
            error(R.drawable.ic_not_avatar_foreground)
            transformations(CircleCropTransformation())
        }

        binding.acceptorDebtUserName.text = acceptorDebtUserName
        binding.acceptorDebtUserIcon.load(acceptorDebtUserIconUrl) {
            error(R.drawable.ic_not_avatar_foreground)
            transformations(CircleCropTransformation())
        }
    }

    override fun showLoading() {
        binding.loading.visibility = View.VISIBLE
        binding.repayBtn.isEnabled = false
        binding.repaySeekBar.visibility = View.INVISIBLE
        binding.repayText.isClickable = false
        binding.repayEditText.visibility  =View.GONE
    }

    override fun hideLoading() {
        binding.repayBtn.isEnabled = true
        binding.repaySeekBar.visibility = View.VISIBLE
        binding.repayText.isClickable = true
        binding.loading.visibility = View.GONE
        binding.repayEditText.visibility  =View.VISIBLE
    }

    override fun showError() {
        hideContent()
        binding.retryButton.isEnabled = true
        binding.retryButton.visibility = View.VISIBLE
        binding.errorContainer.visibility = View.VISIBLE
    }

    override fun closeScreen(userId: Int) {
        showContent()
        router.replaceScreen(CreateDebtScreen.Screens.Chat(userId))
    }
}

package com.mole.android.mole.repay.view

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.core.widget.addTextChangedListener
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
        val maxValue = presenter.onInitMaxValue()
        initEditText(maxValue)
        initSeekBar(maxValue)
        initTextField()
        initRepayButton()
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

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError() {}

    override fun closeScreen(userId: Int) {
        router.replaceScreen(CreateDebtScreen.Screens.Chat(userId))
    }
}

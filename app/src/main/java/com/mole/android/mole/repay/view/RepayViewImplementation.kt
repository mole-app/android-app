package com.mole.android.mole.repay.view

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.SeekBar
import androidx.core.widget.addTextChangedListener
import com.mole.android.mole.*
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

    private val presenter by lazy {
        val repayData = arguments?.getParcelable<RepayData>(ARG_REPAY) ?: RepayData()
        component().repayModule.repayPresenter(repayData)
    }

    private val seekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            binding.repayText.text = requireContext().getString(
                R.string.text_with_ruble_suffix,
                progress.toString()
            )

            setHighLightedText(progress)
            provideEnabledToButton(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
        override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val startValue = presenter.onInitStartValue()
        val maxValue = presenter.onInitMaxValue()
        initEditText()
        initTextField(startValue)
        initSeekBar(maxValue)
        initRepayButton()
    }

    private fun initEditText() {
        with(binding.repayEditText) {
            addTextChangedListener { text ->
                val number = provideNumber(text)
                provideTextToField(number)
                provideTextToToSeekbar(number)
                provideEnabledToButton(number)
            }
        }
    }

    private fun initTextField(startValue: Int) {
        with(binding.repayText) {
            text = requireContext().getString(
                R.string.text_with_ruble_suffix,
                startValue.toString()
            )

            setHighLightedText(startValue)
            setOnClickListener { showKeyboard() }
        }
    }

    private fun initSeekBar(maxValue: Int) {
        with(binding.repaySeekBar) {
            max = maxValue
            setOnSeekBarChangeListener(seekBarChangeListener)
        }
    }


    private fun initRepayButton() {
        with(binding.repayBtn) {
            setOnClickListener {
                if (isEnabled) {
                    presenter.onRepayButtonClick()
                }
            }
        }
    }

    private fun provideTextToField(number: Int) {
        binding.repayText.text = requireContext()
            .getString(R.string.text_with_ruble_suffix, number.toString())

        setHighLightedText(number)
    }

    private fun provideTextToToSeekbar(number: Int) {
        binding.repaySeekBar.progress = number
        binding.repaySeekBar.refreshDrawableState()
    }

    private fun provideEnabledToButton(number: Int) {
        binding.repayBtn.isEnabled = number > 0
    }

    private fun showKeyboard() {
        with(binding.repayEditText) {
            requestFocus()
            openKeyboard()
        }
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

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showError() {
        TODO("Not yet implemented")
    }
}

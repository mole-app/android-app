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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSeekBar()
        init()
    }

    private fun init() {
        binding.repayText.text = "0 ${requireContext().getString(R.string.rubles_suffix)}"
        binding.repayText.setHighLightedText(
            "0",
            color = requireContext().getColor(R.color.white_alpha_50)
        )

        binding.repayEditText.addTextChangedListener { text ->
            provideTextToField(text)
            provideProgressToSeekbar(text)
        }

        binding.repayText.setOnClickListener {
            showKeyboard()
        }
    }

    private fun provideProgressToSeekbar(text: Editable?) {
        val string = text.toString()
        val number = string.toIntOrNull(10)
        val isEmpty = string.isBlank() || number == null || number <= 0
        binding.seekBar.progress = if (isEmpty) 0 else number!!
        binding.seekBar.refreshDrawableState()
    }

    private fun showKeyboard() {
        with(binding.repayEditText) {
            requestFocus()
            openKeyboard()
        }
    }

    private fun provideTextToField(text: Editable?) {
        val string = text.toString()
        val number = string.toIntOrNull(10)
        val isEmpty = string.isBlank() || number == null || number <= 0
        binding.repayText.text =
            "${if (isEmpty) "0" else number} ${requireContext().getString(R.string.rubles_suffix)}"
        if (isEmpty) {
            binding.repayText.setHighLightedText(
                "0",
                color = requireContext().getColor(R.color.white_alpha_50)
            )
        }
    }

    private fun initSeekBar() {
        binding.seekBar.max = 100
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.repayText.text =
                        "${progress} ${requireContext().getString(R.string.rubles_suffix)}"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
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

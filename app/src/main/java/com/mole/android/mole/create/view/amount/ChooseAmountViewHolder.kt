package com.mole.android.mole.create.view.amount

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LifecycleCoroutineScope
import com.mole.android.mole.R
import com.mole.android.mole.create.presentation.ChooseAmountPresenter
import com.mole.android.mole.create.view.CreateDebtScreen
import com.mole.android.mole.create.view.steps.BaseStepsHolder
import com.mole.android.mole.openKeyboard
import com.mole.android.mole.setHighLightedText

@SuppressLint("SetTextI18n")
class ChooseAmountViewHolder(
    parent: ViewGroup,
    override val scope: LifecycleCoroutineScope,
    private val presenter: ChooseAmountPresenter,
    private val onConfirmCreatingListener: (CreateDebtScreen.CreatedDebt) -> Unit
) : BaseStepsHolder(parent, R.layout.holder_choose_amount), ChooseAmountView {

    private val amountText = itemView.findViewById<TextView>(R.id.amount_text)
    private val editText = itemView.findViewById<EditText>(R.id.amount_edit_text)
    private val confirmButton = itemView.findViewById<View>(R.id.amount_confirm_button)

    init {
        amountText.text = "0 ${itemView.context.getString(R.string.rubles_suffix)}"
        amountText.setHighLightedText("0", itemView.context.getColor(R.color.white_alpha_50))
        confirmButton.isEnabled = false
        editText.addTextChangedListener { text ->
            confirmButton.isEnabled = !text.isNullOrEmpty()
            provideTextToField(text)
        }
        amountText.setOnClickListener {
            showKeyboard()
        }
        confirmButton.setOnClickListener {
            editText.text.toString().toIntOrNull()?.let {
                presenter.confirm(it)
            }
        }
    }

    override fun bind() {
        presenter.attachView(this)
    }

    override fun closeScreen(result: CreateDebtScreen.CreatedDebt) {
        onConfirmCreatingListener(result)
    }

    override fun disableButton() {
        confirmButton.isEnabled = false
    }

    override fun enableButton() {
        confirmButton.isEnabled = true
    }

    private fun provideTextToField(text: Editable?) {
        val string = text.toString()
        val number = string.toIntOrNull(10)
        val isEmpty = string.isBlank() || number == null || number <= 0
        amountText.text =
            "${if (isEmpty) "0" else number} ${itemView.context.getString(R.string.rubles_suffix)}"
        if (isEmpty) {
            amountText.setHighLightedText(
                "0",
                itemView.context.getColor(R.color.white_alpha_50)
            )
        }
    }

    private fun showKeyboard() {
        editText.requestFocus()
        editText.openKeyboard()
    }

    override fun requestFocus() {
        showKeyboard()
    }

    override fun showError() {
        Toast.makeText(itemView.context, R.string.loading_error, Toast.LENGTH_SHORT).show()
    }
}
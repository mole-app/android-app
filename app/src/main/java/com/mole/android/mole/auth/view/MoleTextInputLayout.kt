package com.mole.android.mole.auth.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mole.android.mole.R
import com.mole.android.mole.resolveColor

class MoleTextInputLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {

    private lateinit var editTextView: TextInputEditText

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        editTextView = findViewById(R.id.edit_text)
    }

    override fun setErrorEnabled(enabled: Boolean) {
        super.setErrorEnabled(enabled)
        if (!enabled) {
            return
        }
        try {
            changeTextAlignment(
                com.google.android.material.R.id.textinput_error,
                View.TEXT_ALIGNMENT_CENTER
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun changeTextAlignment(textViewId: Int, alignment: Int) {
        val textView: TextView = findViewById(textViewId)
        textView.textAlignment = alignment
    }
}
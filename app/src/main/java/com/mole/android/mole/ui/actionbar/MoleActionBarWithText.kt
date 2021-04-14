package com.mole.android.mole.ui.actionbar

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.mole.android.mole.R

class MoleActionBarWithText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_action_bar_with_text, this)
    }

    private val textView: TextView = findViewById(R.id.textActionBar)

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.MoleActionBarWithText,
                0,
                0
            )
            val textTitle = try {
                val textTitle =
                    typedArray.getString(R.styleable.MoleActionBarWithText_textTitle)
                textTitle
            } finally {
                typedArray.recycle()
            }

            handleAttr(textTitle)

        }
    }

    private fun handleAttr(textTitle: String?) {
        if (textTitle != null) {
            setTextTitleAttr(textTitle)
        }
    }

    private fun setTextTitleAttr(textTitle: String) {
        textView.text = textTitle
    }

}
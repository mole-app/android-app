package com.mole.android.mole.ui.actionbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.Toolbar
import com.mole.android.mole.R

class MoleActionBarWithText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_action_bar_with_text, this)
    }

    private val textView: TextView = findViewById(R.id.textActionBar)
    private val backImageButton: AppCompatImageButton = findViewById(R.id.backActionButtonWithText)

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
            val backVisible = typedArray.getInt(R.styleable.MoleActionBarWithText_backVisible, 0)
            val textTitle = try {
                val textTitle =
                    typedArray.getString(R.styleable.MoleActionBarWithText_textTitle)
                textTitle
            } finally {
                typedArray.recycle()
            }

            handleAttr(textTitle, backVisible)

        }
    }

    private fun handleAttr(textTitle: String?, backVisible: Int) {
        if (textTitle != null) {
            setTextTitleAttr(textTitle)
        }
        setBackVisibleAttr(backVisible)
    }

    private fun setBackVisibleAttr(backVisible : Int) {
        when(backVisible){
            0 -> backImageButton.visibility = View.VISIBLE
            1 -> backImageButton.visibility = View.INVISIBLE
            2 -> backImageButton.visibility = View.GONE

        }
    }

    private fun setTextTitleAttr(textTitle: String) {
        textView.text = textTitle
    }

}
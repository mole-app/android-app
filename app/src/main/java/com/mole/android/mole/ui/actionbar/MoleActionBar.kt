package com.mole.android.mole.ui.actionbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.Toolbar
import androidx.core.view.iterator
import com.mole.android.mole.R
import com.mole.android.mole.setVisibleView


open class MoleActionBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    open fun customView(parent: ViewGroup): View? = null

    init {
        inflate(context, R.layout.view_action_bar_with_text, this)
    }

    private val titleTextView: TextView = findViewById(R.id.text_action_bar)
    private val backImageButton: AppCompatImageButton = findViewById(R.id.back_action_button_with_text)

    init {
        val customViewLinear: LinearLayout = findViewById(R.id.additional_view)
        customView(customViewLinear)

        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.MoleActionBar,
                0,
                0
            )
            val backVisible = typedArray.getBoolean(R.styleable.MoleActionBar_backVisible, false)
            val titleVisible = typedArray.getBoolean(R.styleable.MoleActionBar_titleVisible, false)
            val textTitle = try {
                val textTitle =
                    typedArray.getString(R.styleable.MoleActionBar_textTitle)
                textTitle
            } finally {
                typedArray.recycle()
            }
            handleAttr(textTitle, backVisible, titleVisible)
        }
    }

    fun bindMenu() {
        menu.iterator().forEach { menuItem ->
            val maybeImage: AppCompatImageButton? = menuItem.actionView.findViewById(R.id.menu_icon)
            maybeImage?.setImageDrawable(menuItem.icon)
        }
    }

    private fun handleAttr(textTitle: String?, backVisible: Boolean, titleVisible: Boolean) {
        if (textTitle != null) {
            setTextTitleAttr(textTitle)
        }
        setBackVisibleAttr(backVisible)
        setTitleVisibleAttr(titleVisible)
    }

    private fun setBackVisibleAttr(backVisible: Boolean) {
        backImageButton.setVisibleView(backVisible)
    }

    private fun setTitleVisibleAttr(backVisible: Boolean) {
        titleTextView.setVisibleView(backVisible)
    }

    private fun setTextTitleAttr(textTitle: String) {
        titleTextView.text = textTitle
    }

}
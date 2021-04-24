package com.mole.android.mole.ui.actionbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.Toolbar
import androidx.core.view.iterator
import com.mole.android.mole.R


open class MoleActionBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    open fun customView(): View? = null

    init {
        inflate(context, R.layout.view_action_bar_with_text, this)
    }

    private val titleTextView: TextView = findViewById(R.id.text_action_bar)
    private val backImageButton: AppCompatImageButton = findViewById(R.id.back_action_button_with_text)

    init {
        val customViewLinear: LinearLayout = findViewById(R.id.additional_view)

        val customView = customView()
        if (customView != null) {
            customViewLinear.layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            customViewLinear.addView(customView)
        }

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
        setVisibleView(backImageButton, backVisible)
    }

    private fun setTitleVisibleAttr(backVisible: Boolean) {
        setVisibleView(titleTextView, backVisible)
    }

    private fun setVisibleView(view: View, visible: Boolean) {
        when (visible) {
            true -> view.visibility = View.VISIBLE
            false -> view.visibility = View.GONE
        }
    }

    private fun setTextTitleAttr(textTitle: String) {
        titleTextView.text = textTitle
    }

}
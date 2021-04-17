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
    open fun customViewId(): Int = 0

    init {
        inflate(context, R.layout.view_action_bar_with_text, this)
    }

    private val titleTextView: TextView = findViewById(R.id.textActionBar)
    private val backImageButton: AppCompatImageButton = findViewById(R.id.backActionButtonWithText)

    init {
        val customViewLinear: LinearLayout = findViewById(R.id.additionalView)

        val customView = customView()
        if (customView != null) {
            customViewLinear.layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            customViewLinear.addView(customView)
        }

        val customViewId = customViewId()
        if (customViewId != 0) {
            customViewLinear.layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            inflate(context, customViewId, customViewLinear)
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
            val backVisible = typedArray.getInt(R.styleable.MoleActionBar_backVisible, 0)
            val titleVisible = typedArray.getInt(R.styleable.MoleActionBar_titleVisible, 0)
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

    private fun handleAttr(textTitle: String?, backVisible: Int, titleVisible: Int) {
        if (textTitle != null) {
            setTextTitleAttr(textTitle)
        }
        setBackVisibleAttr(backVisible)
        setTitleVisibleAttr(titleVisible)
    }

    private fun setBackVisibleAttr(backVisible: Int) {
        setVisibleView(backImageButton, backVisible)
    }

    private fun setTitleVisibleAttr(backVisible: Int) {
        setVisibleView(titleTextView, backVisible)
    }

    private fun setVisibleView(view: View, visible: Int) {
        when (visible) {
            0 -> view.visibility = View.VISIBLE
            1 -> view.visibility = View.INVISIBLE
            2 -> view.visibility = View.GONE
        }
    }

    private fun setTextTitleAttr(textTitle: String) {
        titleTextView.text = textTitle
    }

}
package com.mole.android.mole.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt

class DarkView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    @ColorInt
    var colorSelected: Int = 0

    var lastColorView: ColorStateList? = null

    private val rect = Rect()
    var selectedView: View? = null
        set(value) {
            field = value
        }
    private val location = IntArray(2)

    init {
        setBackgroundColor(Color.parseColor("#80212121"))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        selectedView?.apply {
            getLocationOnScreen(location)
            getWindowVisibleDisplayFrame(rect)

            canvas?.clipRect(rect)
            canvas?.translate(location[0].toFloat(), location[1].toFloat())

            lastColorView = selectedView?.backgroundTintList

            if (colorSelected != 0) {
                backgroundTintList = ColorStateList.valueOf(colorSelected)
            }

            draw(canvas)
        }
    }

    fun dismiss() {
        if (colorSelected != 0) {
            selectedView?.backgroundTintList = lastColorView
        }
    }
}
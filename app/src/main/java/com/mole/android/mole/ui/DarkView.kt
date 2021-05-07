package com.mole.android.mole.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class DarkView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val rect = Rect()
    var selectedView: View? = null
    var offsetCutout = 0
    private val location = IntArray(2)
    private val paint = Paint()

    init {
        paint.color = Color.parseColor("#802FC97F")
        setBackgroundColor(Color.parseColor("#80212121"))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        selectedView?.apply {
            fitsSystemWindows = true

            getLocationInWindow(location)

            if (offsetCutout == 0) {
                getGlobalVisibleRect(rect) // without cutout
            } else {
                getWindowVisibleDisplayFrame(rect) // // with cutout
            }

            rect.top -= offsetCutout
            canvas?.clipRect(rect)
            canvas?.translate(location[0].toFloat(), location[1].toFloat() - offsetCutout)

            draw(canvas)
        }
    }

    fun dismiss() {

    }
}
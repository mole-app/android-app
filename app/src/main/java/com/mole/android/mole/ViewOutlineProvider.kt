package com.mole.android.mole

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import kotlin.math.roundToInt

class ViewRoundRectOutlineProvider(
    private val cornerRadius: Float,
    private val bottomCorners: Boolean = true,
    private val topCorners: Boolean = true
) : ViewOutlineProvider() {

    override fun getOutline(view: View, outline: Outline) {
        val topSpace = if (topCorners) 0 else cornerRadius.roundToInt()
        val bottomSpace = if (bottomCorners) 0f else cornerRadius
        val width = view.width.let { if (it == 0) view.measuredWidth else it }
        val height = view.height.let { if (it == 0) view.measuredHeight else it }
        outline.setRoundRect(0, -topSpace, width, (height + bottomSpace).roundToInt(), cornerRadius)
    }
}
package com.mole.android.mole.ui.blur

import kotlin.math.ceil

class ScaleSize(private val scaleFactor: Float) {

    fun scale(width: Int, height: Int): Size {
        val nonRoundedScaledWidth = downscaleSize(width.toFloat())
        val scaledWidth = roundSize(nonRoundedScaledWidth)

        val roundingScaleFactor = width.toFloat() / scaledWidth

        val scaledHeight = Math.ceil((height / roundingScaleFactor).toDouble()).toInt()
        return Size(scaledWidth, scaledHeight, roundingScaleFactor)
    }

    fun isZeroSized(measuredWidth: Int, measuredHeight: Int): Boolean {
        return downscaleSize(measuredHeight.toFloat()) == 0 || downscaleSize(measuredWidth.toFloat()) == 0
    }

    private fun roundSize(value: Int): Int {
        return if (value % ROUNDING_VALUE == 0) {
            value
        } else {
            value - value % ROUNDING_VALUE + ROUNDING_VALUE
        }
    }

    private fun downscaleSize(value: Float): Int {
        return ceil((value / scaleFactor).toDouble()).toInt()
    }

    class Size(val width: Int, val height: Int, val scaleFactor: Float)

    companion object {
        private const val ROUNDING_VALUE = 64
    }
}

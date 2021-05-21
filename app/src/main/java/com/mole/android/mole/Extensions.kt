package com.mole.android.mole

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.AttrRes
import com.mole.android.mole.ui.BorderView
import kotlin.math.sign


fun View.setVisibleView(visible: Boolean) {
    when (visible) {
        true -> this.visibility = View.VISIBLE
        false -> this.visibility = View.GONE
    }
}

fun Context.resolveColor(@AttrRes resId: Int): Int {
    val typedValue = TypedValue()
    val theme = this.theme
    theme.resolveAttribute(resId, typedValue, true)
    return typedValue.data
}

fun Int.isNegative(): Boolean {
    return this.sign == -1
}

fun Int.isPositive(): Boolean {
    return this.sign == 1
}

fun Int.dp(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

fun Float.dp(): Float {
    return this * Resources.getSystem().displayMetrics.density
}

fun View.cornerRadius(radius: Float) {
    clipToOutline = true
    outlineProvider = ViewRoundRectOutlineProvider(radius)
}

private fun View.preparePathForBorder(shape: BorderView.Shape, radiusBorder: Float): Path {
    val rectFBorder = RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat())
    val pathBorder = Path()

    when (shape) {
        BorderView.Shape.RECTANGLE -> {
            val radiusArr = floatArrayOf(
                radiusBorder, radiusBorder,
                radiusBorder, radiusBorder,
                radiusBorder, radiusBorder,
                radiusBorder, radiusBorder
            )
            pathBorder.addRoundRect(
                rectFBorder,
                radiusArr,
                Path.Direction.CW
            )
        }
        BorderView.Shape.OVAL -> {
            pathBorder.addOval(
                rectFBorder,
                Path.Direction.CW
            )
        }
    }
    return pathBorder
}

fun View.setBorder(
    shape: BorderView.Shape,
    radiusBorder: Float,
    lineWidth: Float,
    @AttrRes colorStart: Int,
    @AttrRes colorEnd: Int
) {

    var isCalled = false
    addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
        if (!isCalled) {

            val paintBorder = Paint(Paint.ANTI_ALIAS_FLAG)
            val width = measuredWidth
            val height = measuredHeight
            val bitmap = Bitmap.createBitmap(
                width + lineWidth.toInt(),
                height + lineWidth.toInt(),
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            
            val pathBorder = preparePathForBorder(shape, radiusBorder)

            val gradientStartColor = context.resolveColor(colorStart)
            val gradientEndColor = context.resolveColor(colorEnd)

            paintBorder.shader = LinearGradient(
                0f,
                0f,
                width.toFloat(),
                height.toFloat(),
                gradientStartColor,
                gradientEndColor,
                Shader.TileMode.MIRROR
            )

            paintBorder.style = Paint.Style.STROKE
            paintBorder.strokeWidth = lineWidth

            canvas.translate(lineWidth/2, lineWidth/2)
            canvas.drawPath(pathBorder, paintBorder)
            val drawable = BitmapDrawable(resources, bitmap)
            drawable.setBounds(0, 0, width, height)
            v.overlay.add(drawable)

            isCalled = true
        }
    }

}

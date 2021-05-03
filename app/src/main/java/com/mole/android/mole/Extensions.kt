package com.mole.android.mole

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes
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

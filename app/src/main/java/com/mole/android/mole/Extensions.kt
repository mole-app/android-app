package com.mole.android.mole

import android.content.res.Resources
import android.view.View

fun View.setVisibleView(visible: Boolean) {
    when (visible) {
        true -> this.visibility = View.VISIBLE
        false -> this.visibility = View.GONE
    }
}

fun Int.dp(): Int{
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}
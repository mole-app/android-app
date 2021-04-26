package com.mole.android.mole

import android.content.res.Resources

fun Int.dp(): Int{
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}
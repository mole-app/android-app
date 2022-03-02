package com.mole.android.mole

import java.text.DecimalFormat

fun summaryToString(summary: Int): String {
    val format = DecimalFormat( "###,###.##" )
    format.positivePrefix = "+"
    return format.format(summary)
}

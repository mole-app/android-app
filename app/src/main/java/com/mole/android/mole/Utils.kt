package com.mole.android.mole

import java.text.DecimalFormat

fun summaryToString(summary: Int): String {
    val format = DecimalFormat( "###,###.##" )
    format.positivePrefix = "+"
    return format.format(summary)
}

fun tagsToString(tags: List<String>): String {
    var tagsText = "#${tags.first()}"
    val tagsWithoutFirst = tags.drop(tags.size - 2)
    for (tag in tagsWithoutFirst) {
        tagsText += ", #$tag"
    }
    return tagsText
}

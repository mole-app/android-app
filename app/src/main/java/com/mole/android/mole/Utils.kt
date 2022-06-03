package com.mole.android.mole

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun summaryToString(summary: Long): String {
    val format = DecimalFormat("###,###.##")
    format.positivePrefix = "+ "
    format.negativePrefix = "- "
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

fun dateToString(date: Date): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.ROOT)
    formatter.timeZone = TimeZone.getDefault()
    return formatter.format(date)
}

fun timeToString(time : Date): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.ROOT)
    formatter.timeZone = TimeZone.getDefault()
    return formatter.format(time)
}

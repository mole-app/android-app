package com.mole.android.mole

import java.text.SimpleDateFormat
import java.util.*

fun stringToDate(date: String): Date {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ROOT)
    formatter.timeZone = TimeZone.getTimeZone("GMT")
    return formatter.parse(date) ?: Date()
}

fun dateToString(date: Date): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.ROOT)
    return formatter.format(date)
}

fun timeToString(time : Date): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.ROOT)
    return formatter.format(time)
}
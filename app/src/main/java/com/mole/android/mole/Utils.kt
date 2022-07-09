package com.mole.android.mole

import android.content.Context
import android.content.ContextWrapper
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

fun stringToDate(date: String): Date{
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

fun <T> throttleLatest(
    intervalMs: Long = 300L,
    coroutineScope: CoroutineScope,
    destinationFunction: (T) -> Unit
): (T) -> Unit {
    var throttleJob: Job? = null
    var latestParam: T
    return { param: T ->
        latestParam = param
        if (throttleJob?.isCompleted != false) {
            throttleJob = coroutineScope.launch {
                delay(intervalMs)
                latestParam.let(destinationFunction)
            }
        }
    }
}

fun Context.lifecycleOwner(): LifecycleOwner? {
    var curContext = this
    var maxDepth = 20
    while (maxDepth-- > 0 && curContext !is LifecycleOwner) {
        curContext = (curContext as ContextWrapper).baseContext
    }
    return if (curContext is LifecycleOwner) {
        curContext as LifecycleOwner
    } else {
        null
    }
}

fun EditText.onTextChangeSkipped(skipMs: Long = 300L, action: (String) -> Unit) {
    val delayedAction = context.lifecycleOwner()?.lifecycleScope?.let { scope ->
        throttleLatest(
            intervalMs = 300L,
            scope,
            action
        )
    }
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            delayedAction?.invoke(s?.toString() ?: "")
        }
        override fun afterTextChanged(s: Editable?) {}
    })
}

fun TextView.setHighLightedText(textToHighlight: String, @ColorInt color: Int, ignoreCase: Boolean = true) {
    val tvt = this.text.toString()
    var ofe = tvt.indexOf(textToHighlight, 0, ignoreCase)
    val wordToSpan: Spannable = SpannableString(this.text)
    var ofs = 0
    while (ofs < tvt.length && ofe != -1) {
        ofe = tvt.indexOf(textToHighlight, ofs, ignoreCase)
        if (ofe == -1) break else {
            wordToSpan.setSpan(
                ForegroundColorSpan(color),
                ofe,
                ofe + textToHighlight.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            this.setText(wordToSpan, TextView.BufferType.SPANNABLE)
        }
        ofs = ofe + 1
    }
}

val View.keyboardIsVisible: Boolean
    get() = rootWindowInsets?.let {
        WindowInsetsCompat
            .toWindowInsetsCompat(rootWindowInsets)
            .isVisible(WindowInsetsCompat.Type.ime())
    } ?: false

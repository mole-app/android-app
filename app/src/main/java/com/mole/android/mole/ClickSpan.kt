package com.mole.android.mole

import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView


class ClickSpan(
    private val withUnderline: Boolean = false,
    private val listener: OnClickListener? = null
) : ClickableSpan() {

    fun interface OnClickListener {
        fun onClick()
    }

    companion object {
        fun clickLink(
            view: TextView, clickableText: String,
            withUnderline: Boolean = true,
            listener: OnClickListener?
        ) {
            val text = view.text
            val string = text.toString()
            val span = ClickSpan(withUnderline, listener)
            val start = string.indexOf(clickableText)
            val end = start + clickableText.length
            if (start == -1) {
                return
            }
            if (text is Spannable) {
                text.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else {
                val s = SpannableString.valueOf(text)
                s.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                view.text = s
            }
            val m = view.movementMethod
            if (m !is LinkMovementMethod) {
                view.movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }

    override fun onClick(view: View) {
        listener?.onClick()
    }

    override fun updateDrawState(paint: TextPaint) {
        super.updateDrawState(paint)
        paint.isUnderlineText = withUnderline
    }
}
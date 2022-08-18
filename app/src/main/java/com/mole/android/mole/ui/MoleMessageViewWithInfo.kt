package com.mole.android.mole.ui

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.mole.android.mole.*
import kotlin.math.absoluteValue

class MoleMessageViewWithInfo @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        const val balanceDefaultValue: Int = 0
        const val tagDefaultValue: String = ""
        const val timeDefaultValue: String = ""
        const val isReadDefaultValue: Boolean = false
    }

    var balance = balanceDefaultValue
        set(value) {
            updateBalance(value)
            field = value
        }

    var tag: String? = tagDefaultValue
        set(value) {
            updateTag(value)
            field = value
        }

    var time = timeDefaultValue
        set(value) {
            updateTime(value)
            field = value
        }

    var isRead = isReadDefaultValue
        set(value) {
            updateCheckMark(value)
            field = value
        }

    var isDeleted = false
        set(value) {
            updateColor(balance, value)
            field = value
        }

    @ColorInt
    private val colorAccept: Int

    @ColorInt
    private val colorDeny: Int

    @ColorInt
    private val colorDisabled: Int

    private var postfix: String = ""
    private var isCheckMark: Boolean = false

    private val balanceTextView: TextView
    private val tagTextView: TextView
    private val timeTextView: TextView
    private val checkMarkImageView: ImageView

    init {
        background = ResourcesCompat.getDrawable(resources, R.drawable.shape_massage, null)

        colorAccept = context.resolveColor(R.attr.colorAccept)
        colorDisabled = context.resolveColor(R.attr.colorDisabled)
        colorDeny = context.resolveColor(R.attr.colorDeny)

        init(context, attrs)

        balanceTextView = findViewById(R.id.balance)
        tagTextView = findViewById(R.id.tag)
        timeTextView = findViewById(R.id.time)
        checkMarkImageView = findViewById(R.id.check_mark)

        setVisibilityImageView()
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.MoleMessageViewWithInfo,
                0,
                0
            )
            postfix =
                typedArray.getString(R.styleable.MoleMessageViewWithInfo_postfix_with_info) ?: ""
            isCheckMark =
                typedArray.getBoolean(R.styleable.MoleMessageViewWithInfo_check_marks, true)
            typedArray.recycle()
            inflateView()
        }
    }

    private fun inflateView() {
        inflate(context, R.layout.view_message_with_info, this)
        setPaddingRelative(
            16.dp,
            0,
            16.dp,
            0
        )
    }

    private fun setVisibilityImageView() {
        if (isCheckMark) checkMarkImageView.visibility = View.VISIBLE
        else checkMarkImageView.visibility = View.GONE
    }

    private fun updateBalance(value: Int) {
        val sign = updateColor(value, isDeleted)

        balanceTextView.text = context.getString(
            R.string.mole_message_balance,
            sign,
            value.absoluteValue,
            postfix
        )
    }

    private fun updateColor(value: Int, isDeleted: Boolean): String {
        val sign : String
        when {
            value.isNegative() -> {
                sign = context.getString(R.string.minus_prefix)
                backgroundTintList = ColorStateList.valueOf(colorDeny)
            }
            value.isPositive() -> {
                sign = context.getString(R.string.plus_prefix)
                backgroundTintList = ColorStateList.valueOf(colorAccept)
            }
            else -> {
                sign = ""
                backgroundTintList = ColorStateList.valueOf(colorDisabled)
            }
        }

        if (isDeleted) {
            backgroundTintList = ColorStateList.valueOf(colorDisabled)
        }
        return sign
    }

    private fun updateTag(value: String?) {
        tagTextView.text = value
    }

    private fun updateTime(value: String) {
        timeTextView.text = if (value.isNotEmpty()) value else ""
    }

    private fun updateCheckMark(value: Boolean) {
        when (value) {
            true -> {
                checkMarkImageView.setImageResource(R.drawable.ic_check_mark_read)
            }
            false -> {
                checkMarkImageView.setImageResource(R.drawable.ic_check_mark_send)
            }
        }
    }
}
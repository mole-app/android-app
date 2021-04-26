package com.mole.android.mole

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.res.ColorStateList
import android.graphics.Point
import android.graphics.PointF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.*
import android.view.View.OnLongClickListener
import android.view.View.OnTouchListener
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.mole.android.mole.ui.PopupView
import kotlin.math.absoluteValue
import kotlin.math.sign


class MoleMessageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    @ColorInt
    private val colorAccept: Int

    @ColorInt
    private val colorDeny: Int

    @ColorInt
    private val colorDisabled: Int

    @ColorInt
    private val colorSelected: Int

    private var postfix: String = ""

    var balance = 0
        set(value) {
            updateBalance(value)
            field = value
        }
    companion object {
        var defaultValue: Int = 0
    }

    private val balanceTextView: TextView

    init {
        background = ResourcesCompat.getDrawable(resources, R.drawable.shape_massage, null)

        val typedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(R.attr.colorAccept, typedValue, true)
        colorAccept = typedValue.data
        theme.resolveAttribute(R.attr.colorDisabled, typedValue, true)
        colorDisabled = typedValue.data
        theme.resolveAttribute(R.attr.colorDeny, typedValue, true)
        colorDeny = typedValue.data
        theme.resolveAttribute(R.attr.colorAccent, typedValue, true)
        colorSelected = typedValue.data

        init(context, attrs)

        balanceTextView = findViewById(R.id.balance)

        balance = defaultValue
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.MoleMessageView,
                0,
                0
            )
            val (additionalInfo, postfix) = try {
                val additionalInfo =
                    typedArray.getBoolean(R.styleable.MoleMessageView_additional_info, true)

                val postfix = typedArray.getString(R.styleable.MoleMessageView_postfix)

                additionalInfo to postfix
            } finally {
                typedArray.recycle()
            }

            handleAttr(additionalInfo, postfix)

        }
    }

    private fun handleAttr(additionalInfo: Boolean, postfix: String?) {
        setAdditionalInfoAttr(additionalInfo)
        if (postfix != null) {
            setPostfixAttr(postfix)
        }
    }

    private fun setPostfixAttr(postfix: String) {
        this.postfix = postfix
    }

    private fun setAdditionalInfoAttr(additionalInfo: Boolean) {
        when (additionalInfo) {
            false -> {
                inflate(context, R.layout.view_message, this)
                setPaddingRelative(
                    8.dp(),
                    0,
                    8.dp(),
                    0
                )
            }
            true -> {
                inflate(context, R.layout.view_message_with_info, this)
                setPaddingRelative(
                    16.dp(),
                    0,
                    16.dp(),
                    0
                )
            }
        }
    }

    private fun updateBalance(value: Int) {
        val sign: String
        when (value.sign) {
            1 -> {
                sign = context.getString(R.string.plus_prefix)
                backgroundTintList = ColorStateList.valueOf(colorAccept)
            }
            -1 -> {
                sign = context.getString(R.string.minus_prefix)
                backgroundTintList = ColorStateList.valueOf(colorDeny)
            }
            else -> {
                sign = ""
                backgroundTintList = ColorStateList.valueOf(colorDisabled)
            }
        }
        balanceTextView.text = "$sign${value.absoluteValue}$postfix"
    }

}
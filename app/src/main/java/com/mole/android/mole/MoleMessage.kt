package com.mole.android.mole

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import kotlin.math.absoluteValue
import kotlin.math.sign

class MoleMessage @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_message, this)
        background = ResourcesCompat.getDrawable(resources, R.drawable.shape_massage, null)
        val paddingEnd = (16 * Resources.getSystem().displayMetrics.density).toInt()
        val paddingStart = (16 * Resources.getSystem().displayMetrics.density).toInt()
        val paddingBottom = (16 * Resources.getSystem().displayMetrics.density).toInt()
        val paddingTop = (16 * Resources.getSystem().displayMetrics.density).toInt()
        setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom)
    }

    private val tagTextView: TextView = findViewById(R.id.tag)
    private val timeTextView: TextView = findViewById(R.id.time)
    private val balanceTextView: TextView = findViewById(R.id.balance)


    //    @ColorInt
    private val colorAccept: Int

    init {
        val typedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(R.attr.colorAccept, typedValue, true)
        colorAccept = typedValue.data
    }

    private val colorDeny: Int

    init {
        val typedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(R.attr.colorDeny, typedValue, true)
        colorDeny = typedValue.data
    }

    private var postfix: String = ""

    var balance = 0
        set(value) {
            val sign: String
            when (value.sign) {
                1 -> {
                    sign = "+ "
                    backgroundTintList = context.resources.getColorStateList(R.color.color_accept, null)
                }
                -1 -> {
                    sign = "- "
                    backgroundTintList = context.resources.getColorStateList(R.color.color_deny, null)
                }
                else -> {
                    sign = ""
                }
            }

            balanceTextView.text = "$sign${value.absoluteValue} $postfix"
            field = value
        }

    init {
        init(context, attrs)
        balance = -4000
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.MoleMessage,
                0,
                0
            )
            val (additionalInfo, postfix) = try {
                val additionalInfo =
                    typedArray.getInt(R.styleable.MoleMessage_additional_info, 0)

                val postfix = typedArray.getString(R.styleable.MoleMessage_postfix)

                additionalInfo to postfix
            } finally {
                typedArray.recycle()
            }

            handleAttr(additionalInfo, postfix)

        }
    }

    private fun handleAttr(additionalInfo: Int, postfix: String?) {
        setAdditionalInfoAttr(additionalInfo)
        if (postfix != null) {
            setPostfixAttr(postfix)
        }
    }

    private fun setPostfixAttr(postfix: String) {
        this.postfix = postfix
    }

    private fun setAdditionalInfoAttr(additionalInfo: Int) {

        when (additionalInfo) {
            0 -> {
                tagTextView.visibility = View.VISIBLE
                timeTextView.visibility = View.VISIBLE
                val paddingEnd = (16 * Resources.getSystem().displayMetrics.density).toInt()
                val paddingStart = (16 * Resources.getSystem().displayMetrics.density).toInt()
                val paddingBottom = (16 * Resources.getSystem().displayMetrics.density).toInt()
                val paddingTop = (16 * Resources.getSystem().displayMetrics.density).toInt()
                setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom)

            }
            1 -> {
                tagTextView.visibility = View.GONE
                timeTextView.visibility = View.GONE
                val paddingEnd = (16 * Resources.getSystem().displayMetrics.density).toInt()
                val paddingStart = (16 * Resources.getSystem().displayMetrics.density).toInt()
                val paddingBottom = (8 * Resources.getSystem().displayMetrics.density).toInt()
                val paddingTop = (8 * Resources.getSystem().displayMetrics.density).toInt()
                setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom)
            }
        }
    }

}
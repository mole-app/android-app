package com.mole.android.mole.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.mole.android.mole.R

class ProgressButtonView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val button: AppCompatButton
    private val progress: ProgressBar

    private var buttonText = ""
    private var buttonIconResId = ResourcesCompat.ID_NULL
    private var buttonTint = Color.TRANSPARENT
    private var progressTint = Color.TRANSPARENT
    private var textColor = Color.WHITE

    fun setButtonClickListener(onClick: () -> Unit) {
        button.setOnClickListener {
            setProgress(true)
            onClick.invoke()
        }
    }

    fun setProgress(isProgress: Boolean) {
        progress.isVisible = isProgress
        button.isEnabled = !isProgress
        button.text = updateText(!isProgress)
    }

    fun setButtonEnabled(isEnabled: Boolean) {
        button.isEnabled = isEnabled
        progress.isVisible = false
    }

    init {
        initAttrs(context, attrs)

        inflate(context, R.layout.view_progress_button, this)
        button = findViewById(R.id.button)
        progress = findViewById(R.id.progress)

        button.text = buttonText
        button.setTextColor(textColor)
        button.backgroundTintList = ColorStateList.valueOf(buttonTint)
        button.setCompoundDrawablesWithIntrinsicBounds(buttonIconResId, 0, 0, 0)

        progress.indeterminateTintList = ColorStateList.valueOf(progressTint)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.ProgressButtonView,
                0,
                0
            )

            buttonText = typedArray.getString(R.styleable.ProgressButtonView_button_text) ?: ""
            buttonIconResId = typedArray.getResourceId(
                R.styleable.ProgressButtonView_button_icon,
                R.drawable.circle_gray
            )
            buttonTint = typedArray.getColor(
                R.styleable.ProgressButtonView_button_tint,
                Color.TRANSPARENT
            )
            progressTint = typedArray.getColor(
                R.styleable.ProgressButtonView_progress_tint,
                Color.TRANSPARENT
            )
            textColor = typedArray.getColor(
                R.styleable.ProgressButtonView_text_color,
                Color.WHITE
            )
            typedArray.recycle()
        }
    }

    private fun updateText(isVisible: Boolean): String {
        return when (isVisible) {
            true -> buttonText
            else -> ""
        }
    }
}
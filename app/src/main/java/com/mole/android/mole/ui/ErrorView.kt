package com.mole.android.mole.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.mole.android.mole.R
import com.mole.android.mole.Shape
import com.mole.android.mole.dp
import com.mole.android.mole.setupBorder

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val retryButton: AppCompatButton

    private var retryOnClick: (() -> Unit)? = null

    init {
        inflateView()

        retryButton = findViewById(R.id.retry_button)
        retryButton.setupBorder(Shape.RECTANGLE, 80f.dp)
        retryButton.setOnClickListener {
            if (retryOnClick != null) {
                retryOnClick?.invoke()
                retryButton.isEnabled = false
            }
        }
    }

    private fun inflateView() {
        inflate(context, R.layout.layout_error, this)
    }

    fun setRetryClickListener(retry: () -> Unit) {
        retryOnClick = retry
    }

    fun showView() {
        this.visibility = View.VISIBLE
        retryButton.isEnabled = true
    }

    fun hideView() {
        this.visibility = View.GONE
    }
}

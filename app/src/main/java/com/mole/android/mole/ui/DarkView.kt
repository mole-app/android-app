package com.mole.android.mole.ui

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.mole.android.mole.R

class DarkView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val rect = Rect()
    var selectedView: View? = null
    set(value) {
        value?.getLocationInWindow(location)
        if (offsetCutout == 0) {
            selectedView?.getGlobalVisibleRect(rect) // without cutout
        } else {
            selectedView?.getWindowVisibleDisplayFrame(rect) // // with cutout
        }
        field = value
    }
    var offsetCutout = 0
    set(value) {
        if (offsetCutout == 0) {
            selectedView?.getGlobalVisibleRect(rect) // without cutout
        } else {
            selectedView?.getWindowVisibleDisplayFrame(rect) // // with cutout
        }
        field = value
    }

    private val fixRect = Rect()
    private val location = IntArray(2)
    private val paint = Paint()

    init {
        paint.color = Color.parseColor("#802FC97F")
        startColorAnimation()
//        setBackgroundColor(Color.parseColor("#80212121"))
    }

    private fun startColorAnimation() {

        val animator: ObjectAnimator = ObjectAnimator.ofObject(
            this,
            "backgroundColor",
            ArgbEvaluator(),
            Color.TRANSPARENT,
            Color.parseColor("#A0212121")
        )

        animator.interpolator = DecelerateInterpolator()
        animator.duration = 300

        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int
            this.backgroundTintList = ColorStateList.valueOf(animatedValue)
        }

        animator.start()
    }

    private fun reverseColorAnimation() {
        val animator: ObjectAnimator = ObjectAnimator.ofObject(
            this,
            "backgroundColor",
            ArgbEvaluator(),
            Color.parseColor("#A0212121"),
            Color.TRANSPARENT
        )

        animator.interpolator = DecelerateInterpolator()
        animator.duration = 300

        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int
            this.backgroundTintList = ColorStateList.valueOf(animatedValue)
        }

        animator.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        selectedView?.apply {
            fitsSystemWindows = true

            fixRect.set(rect)
            fixRect.top -= offsetCutout
            canvas?.clipRect(fixRect)
            canvas?.translate(location[0].toFloat(), location[1].toFloat() - offsetCutout)

            draw(canvas)
        }
    }

    fun dismiss() {
        reverseColorAnimation()
    }
}
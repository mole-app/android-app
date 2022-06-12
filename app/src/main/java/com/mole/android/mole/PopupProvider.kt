package com.mole.android.mole

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.os.Build
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.mole.android.mole.ui.PopupView
import com.mole.android.mole.ui.blur.BlurView


class PopupProvider(
    private val context: Context,
    private val scrollView: RecyclerView,
    private val rootView: View
) {
    val touchListener = View.OnTouchListener { view, event -> // save the X,Y coordinates

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchDown.x = event.x
                lastTouchDown.y = event.y
            }
            MotionEvent.ACTION_UP -> view.performClick()
            else -> {
            }
        }

        false
    }

    private val resources: Resources
        get() = context.resources

    // class member variable to save the X,Y coordinates
    private val lastTouchDown = PointF()

    @ColorInt
    private var colorTint: Int = 0
    private var selectedView: View? = null
    private var popupWindow: PopupView? = null

    private val popupView: BlurView =
        View.inflate(this.context, R.layout.view_popup_window, null) as BlurView

    init {
        val root = rootView.rootView as? ViewGroup
        popupView.setupWith(root).setupBlurRadius(12f).setupCornerRadius(8f.dp).setupBorder()

        val editButton: Button = popupView.findViewById(R.id.edit_popup)
        editButton.setOnClickListener {
            popupWindow?.dismiss()
        }

        val deleteButton: Button = popupView.findViewById(R.id.delete_popup)
        deleteButton.setOnClickListener {
            popupWindow?.dismiss()
            val myDialogFragment = MoleAlertDialog()
            myDialogFragment.rootView = root
            val activity = rootView.context as? FragmentActivity
            activity?.apply {
                val manager = this.supportFragmentManager
                myDialogFragment.show(manager, "myDialog")
            }
        }
    }

    private fun compareHighRect(outRect: Rect, innerRect: Rect): Int {

        if (outRect.top > innerRect.top) {
            return innerRect.top - outRect.top
        }

        if (outRect.bottom < innerRect.bottom) {
            return innerRect.bottom - outRect.bottom
        }
        return 0
    }

    private fun onAnimationEnd(animView: View, position: Position) {
        val rect = Rect()
        val visibleRect = Rect()
        animView.getDrawingRect(rect)
        val l = IntArray(2)
        animView.getLocationOnScreen(l)
        rect.left += l[0]
        rect.right += l[0]
        rect.bottom += l[1]
        rect.top += l[1]

        animView.getWindowVisibleDisplayFrame(visibleRect)

        val highBottomBar =
            resources.getDimension(R.dimen.design_fab_image_size).toInt()
        val invisibleDiff =
            resources.getDimension(R.dimen.mole_message_margin_selected).toInt()
        val bottomInvisibleDiff = highBottomBar + invisibleDiff
        visibleRect.bottom -= bottomInvisibleDiff
        visibleRect.top += invisibleDiff
        val diff: Int = compareHighRect(visibleRect, rect)

        if (diff == 0) {
            scrollView.addOnItemTouchListener(RecyclerViewDisabler)
            longClickOnMessage(animView, position)
        } else {
            scrollView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    when (newState) {
                        SCROLL_STATE_IDLE -> {
                            longClickOnMessage(animView, position)
                            recyclerView.removeOnScrollListener(this)
                        }
                    }
                }

            })
            scrollView.addOnItemTouchListener(RecyclerViewDisabler)
            scrollView.smoothScrollBy(0, diff)
        }
    }

    private fun longClickOnMessage(view: View, position: Position) {
        val location = IntArray(2)
        view.getLocationInWindow(location)
        val point = Point()
        point.x = location[0]
        point.y = location[1]

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        if (inflater != null) {
            popupWindow = PopupView(
                popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true // lets taps outside the popup also dismiss it
            )

            val offsetCutout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val activity = view.context as? FragmentActivity
                activity?.window?.decorView?.rootWindowInsets?.stableInsetTop ?: 0
            } else {
                0
            }

            popupWindow?.apply {

                animationStyle = R.style.AnimationPopup
                setOnDismissListener {
                    scrollView.removeOnItemTouchListener(RecyclerViewDisabler)
                    reverseColorAnimation()
                }
                this.selectedView = view
                this.offsetCutout = offsetCutout
                // dismiss the popup window when touched
                popupView.setOnClickListener {
                    dismiss()
                }
                val offset: Int = when (position) {
                    Position.RIGHT -> - context.resources.getDimension(R.dimen.popup_width).toInt()
                    else -> 0
                }

                val x = lastTouchDown.x.toInt() + point.x + offset
                val y = lastTouchDown.y.toInt() + point.y
                showAtLocation(view, Gravity.START or Gravity.TOP, x, y)
            }
        }
    }

    fun start(view: View, position: Position = Position.LEFT) {
        startColorAnimation(view, position)
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun startColorAnimation(
        animationView: View,
        position: Position
    ) {
        selectedView = animationView
        colorTint = animationView.backgroundTintList!!.defaultColor
        val animator: ObjectAnimator = ObjectAnimator.ofObject(
            animationView,
            "backgroundTint",
            ArgbEvaluator(),
            animationView.backgroundTintList!!.defaultColor,
            ContextCompat.getColor(context, R.color.color_accent)
        )

        animator.interpolator = DecelerateInterpolator()
        animator.duration = resources.getInteger(R.integer.duration_animation).toLong()

        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int
            animationView.backgroundTintList = ColorStateList.valueOf(animatedValue)
        }

        animator.start()
        animator.doOnEnd {
            onAnimationEnd(animationView, position)
        }
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun reverseColorAnimation() {

        val animator: ObjectAnimator = ObjectAnimator.ofObject(
            selectedView,
            "backgroundTint",
            ArgbEvaluator(),
            selectedView!!.backgroundTintList!!.defaultColor,
            colorTint
        )

        animator.interpolator = DecelerateInterpolator()
        animator.duration = resources.getInteger(R.integer.duration_animation).toLong()

        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int
            selectedView!!.backgroundTintList = ColorStateList.valueOf(animatedValue)
        }

        animator.start()
    }

    enum class Position {
        RIGHT,
        LEFT
    }
}
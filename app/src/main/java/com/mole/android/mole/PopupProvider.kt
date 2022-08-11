package com.mole.android.mole

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Point
import android.graphics.PointF
import android.os.Build
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.ui.PopupView
import com.mole.android.mole.ui.blur.BlurView


class PopupProvider<T>(
    private val context: Context,
    private val scrollView: RecyclerView,
    private val rootView: View,
    isEditDisable: Boolean = false,
    isDeleteDisable: Boolean = false,
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
    private var deleteListener: ((View, T) -> Unit)? = null

    @ColorInt
    private var colorTint: Int = 0
    private var selectedView: View? = null
    private var popupWindow: PopupView? = null
    private var currentItem: T? = null
    private var currentSelectView: View? = null

    private val popupView: BlurView =
        View.inflate(this.context, R.layout.view_popup_window, null) as BlurView

    init {
        val root = rootView.rootView as? ViewGroup
        popupView.setupWith(root).setupBlurRadius(12f).setupCornerRadius(8f.dp).setupBorder()

        val editButton: Button = popupView.findViewById(R.id.edit_popup)
        val deleteButton: Button = popupView.findViewById(R.id.delete_popup)
        if (isEditDisable) {
            editButton.visibility = View.GONE
            val params =
                ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, 48.dp)
            params.setMargins(0, 0, 0, 0)
            deleteButton.layoutParams = params
        } else {
            editButton.setOnClickListener {
                popupWindow?.dismiss()
            }
        }

        if (isDeleteDisable) {
            deleteButton.visibility = View.GONE
        } else {
            deleteButton.setOnClickListener { _ ->
                popupWindow?.dismiss()
                val myDialogFragment = MoleAlertDialog()
                myDialogFragment.rootView = root
                myDialogFragment.setOnAcceptListener {
                    currentItem?.let { current ->
                        currentSelectView?.let {
                            deleteListener?.invoke(
                                it,
                                current
                            )
                        }
                    }
                }
                val activity = rootView.context as? FragmentActivity
                activity?.apply {
                    val manager = this.supportFragmentManager
                    myDialogFragment.show(manager, "myDialog")
                }
            }
        }
    }


    fun start(view: View, item: T, position: Position = Position.LEFT) {
        scrollView.addOnItemTouchListener(RecyclerViewDisabler)
        currentItem = item
        currentSelectView = view
        startColorAnimation(view, position)
    }

    fun setOnDeleteListener(listener: (View, T) -> Unit) {
        deleteListener = listener
    }

    private fun onAnimationEnd(animView: View, position: Position) {
        longClickOnMessage(animView, position)
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
                    Position.RIGHT -> -context.resources.getDimension(R.dimen.popup_width).toInt()
                    else -> 0
                }

                val x = lastTouchDown.x.toInt() + point.x + offset
                val y = lastTouchDown.y.toInt() + point.y
                showAtLocation(view, Gravity.START or Gravity.TOP, x, y)
            }
        }
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
            context.resolveColor(R.attr.colorAccent)
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
package com.mole.android.mole

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.graphics.PointF
import android.os.Build
import android.util.DisplayMetrics
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
    @ColorInt private val selectColor: Int,
    isEditDisable: Boolean = false,
    isDeleteDisable: Boolean = false,
    isBalanceDisable: Boolean = false
) {
    @SuppressLint("ClickableViewAccessibility")
    val touchListener = View.OnTouchListener { view, event -> // save the X,Y coordinates

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchDown.x = event.x
                lastTouchDown.y = event.y
            }
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
    private var balanceListener: ((View, T) -> Unit)? = null

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
        val balanceButton: Button = popupView.findViewById(R.id.balance_popup)

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

        if (isBalanceDisable) {
            balanceButton.visibility = View.GONE
        } else {
            balanceButton.setOnClickListener { _ ->
                popupWindow?.dismiss()
                currentItem?.let { current ->
                    currentSelectView?.let {
                        balanceListener?.invoke(
                            it,
                            current
                        )
                    }
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

    fun setOnBalanceListener(listener: (View, T) -> Unit) {
        balanceListener = listener
    }

    private fun onAnimationEnd(animView: View, position: Position) {
        clickOnMessage(animView, position)
    }

    private fun clickOnMessage(view: View, position: Position) {
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
                val gravity: Int
                val x: Int
                when (position) {
                    Position.RIGHT -> {
                        val displayMetrics = DisplayMetrics()
                        val wm = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
                        wm?.apply {
                            wm.defaultDisplay.getMetrics(displayMetrics)
                        }
                        val screenWidth = displayMetrics.widthPixels

                        gravity = Gravity.TOP or Gravity.END
                        x = screenWidth - (lastTouchDown.x.toInt() + point.x)
                    }
                    else -> {
                        gravity = Gravity.TOP or Gravity.START
                        x = lastTouchDown.x.toInt() + point.x
                    }
                }

                val y = lastTouchDown.y.toInt() + point.y
                showAtLocation(view, gravity, x, y)
            }
        }
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun startColorAnimation(
        animationView: View,
        position: Position
    ) {
        selectedView = animationView
        colorTint = animationView.backgroundTintList?.defaultColor ?: Color.TRANSPARENT
        val animator: ObjectAnimator = ObjectAnimator.ofObject(
            animationView,
            "backgroundTint",
            ArgbEvaluator(),
            colorTint,
            selectColor
        )

        animator.interpolator = DecelerateInterpolator()
        animator.duration = 10

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

package com.mole.android.mole.test

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mole.android.mole.*
import com.mole.android.mole.ui.BlurView
import com.mole.android.mole.ui.MoleScrollView
import com.mole.android.mole.ui.actionbar.MoleActionBar


class FragmentTest : Fragment() {

    private var toolbar: MoleActionBar? = null

    private var scrollView: MoleScrollView? = null

    var popupWindow: PopupWindow? = null

    @ColorInt
    private var colorSelected: Int = 0

    // class member variable to save the X,Y coordinates
    private val lastTouchDown = PointF()

    // the purpose of the touch listener is just to store the touch X,Y coordinates
    private val touchListener = View.OnTouchListener { view, event -> // save the X,Y coordinates

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchDown.x = event.x
                lastTouchDown.y = event.y
            }
            MotionEvent.ACTION_UP -> view.performClick()
            else -> {
            }
        }

        // let the touch event pass on to whoever needs it
        false
    }

    lateinit var popupView: RelativeLayout
    lateinit var blurView: BlurView

    private fun longClickOnMessage(view: View) {

        val x = lastTouchDown.x
        val y = lastTouchDown.y
        val location = IntArray(2)
        view.getLocationInWindow(location)
        val point = Point()
        point.x = location[0]
        point.y = location[1]

        click(view, x.toInt() + point.x, y.toInt() + point.y)
        view.backgroundTintList
    }

    private fun click(view: View, x: Int = 0, y: Int = 0) {

        // create the popup window
        popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true // lets taps outside the popup also dismiss it
        )

        popupWindow?.apply {

            animationStyle = R.style.AnimationPopup
            setOnDismissListener {
                scrollView?.isScrollable = true
                reverseColorAnimation()
            }
            // dismiss the popup window when touched
            popupView.setOnClickListener {
                dismiss()
            }

            // show the popup window
            showAtLocation(view, Gravity.NO_GRAVITY, x, y)
        }

    }

    @ColorInt
    private var colorTint: Int = 0
    private var selectedView: View? = null

    interface ViewEventCallback {
        fun eventEnd(view: View)
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun startColorAnimation(animationView: View, callback: ViewEventCallback) {
        selectedView = animationView
        colorTint = animationView.backgroundTintList!!.defaultColor
        val animator: ObjectAnimator = ObjectAnimator.ofObject(
            animationView,
            "backgroundTint",
            ArgbEvaluator(),
            animationView.backgroundTintList!!.defaultColor,
            ContextCompat.getColor(requireContext(), R.color.color_accent)
        )

        animator.interpolator = DecelerateInterpolator()
        animator.duration = resources.getInteger(R.integer.duration_animation).toLong()

        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int
            animationView.backgroundTintList = ColorStateList.valueOf(animatedValue)
        }

        animator.start()
        animator.doOnEnd {
            callback.eventEnd(animationView)
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

    override fun onPause() {
        super.onPause()
        popupWindow?.dismiss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.mole_toolbar_with_text)
        scrollView = view.findViewById(R.id.test_main_scroll)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        val moleMessageView: MoleMessageView = view.findViewById(R.id.test_mole_message)

        moleMessageView.setOnTouchListener(touchListener)

        moleMessageView.setOnLongClickListener {
            scrollView?.isScrollable = false
            startColorAnimation(it, object : ViewEventCallback {
                override fun eventEnd(view: View) {
                    longClickOnMessage(view)
                }
            })
            true
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            requireActivity().window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
        }

        popupPreparation(view)
    }

    private fun popupPreparation(view: View) {
        popupView = View.inflate(this.context, R.layout.view_popup_window, null) as RelativeLayout

        blurView = popupView.findViewById(R.id.blur_popup)
        blurView.setupWith(view.rootView as ViewGroup).setBlurRadius(12f)
        blurView.cornerRadius(8f.dp())

        val editButton: Button = popupView.findViewById(R.id.edit_popup)
        editButton.setOnClickListener {
            popupWindow?.dismiss()
        }

        val deleteButton: Button = popupView.findViewById(R.id.delete_popup)
        deleteButton.setOnClickListener {
            popupWindow?.dismiss()
            val myDialogFragment = MoleAlertDialog()
            myDialogFragment.rootView = view.rootView as ViewGroup
            val manager = requireActivity().supportFragmentManager
            myDialogFragment.show(manager, "myDialog")
//            myDialogFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.test_screen, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        colorSelected = context.resolveColor(R.attr.colorAccent)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // First clear current all the menu items
        menu.clear()

        // Add the new menu items
        inflater.inflate(R.menu.history_menu, menu)
        toolbar?.bindMenu()
    }
}
package com.mole.android.mole.test

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mole.android.mole.*
import com.mole.android.mole.ui.BlurView
import com.mole.android.mole.ui.MoleScrollView
import com.mole.android.mole.ui.PopupView
import com.mole.android.mole.ui.actionbar.MoleActionBar


class FragmentTest : Fragment() {

    private var toolbar: MoleActionBar? = null

    private var scrollView: MoleScrollView? = null

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

    private val longClickListener = View.OnLongClickListener {

        val x = lastTouchDown.x
        val y = lastTouchDown.y
        val location = IntArray(2)
        it.getLocationOnScreen(location)
        val point = Point()
        point.x = location[0]
        point.y = location[1]

        click(it, x.toInt() + point.x, y.toInt() + point.y)
        it.backgroundTintList

        true
    }

    private fun click(view: View, x: Int = 0, y: Int = 0) {
        // inflate the layout of the popup window
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        if (inflater != null) {

            val popupView =
                View.inflate(this.context, R.layout.view_popup_window, null) as RelativeLayout

            val blurView: BlurView = popupView.findViewById(R.id.blur_popup)
            blurView.setupWith(view.rootView as ViewGroup).setBlurRadius(12f)

            val windowBackground: Drawable? =
                ContextCompat.getDrawable(requireActivity(), R.drawable.dialog_background);
            blurView.cornerRadius(8f.dp())

            // create the popup window
            val popupWindow = PopupView(
                popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true // lets taps outside the popup also dismiss it
            )

            popupWindow.colorSelected = colorSelected

            popupWindow.selectedView = view

            // dismiss the popup window when touched
            popupView.setOnClickListener {
                popupWindow.dismiss()
            }

            // show the popup window
            popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, x, y)
            popupWindow.isFocusable = true
            popupWindow.isTouchable = true
            popupWindow.update()

            popupWindow.onDismissListener = onDismiss
        }
    }

    private val onDismiss = object : PopupView.OnDismissListener {
        override fun onDismiss() {
            scrollView?.isScrollable = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.test_screen, container, false)

        toolbar = view.findViewById(R.id.mole_toolbar_with_text)
        scrollView = view.findViewById(R.id.test_main_scroll)
        val moleMessageView: MoleMessageView = view.findViewById(R.id.test_mole_message)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        moleMessageView.setOnTouchListener(touchListener)

        moleMessageView.setOnLongClickListener {
            Log.i("point", "click")
            val rect = Rect()
            val visibleRect = Rect()
            it.getDrawingRect(rect)
            val l = IntArray(2)
            it.getLocationOnScreen(l)
            rect.left += l[0]
            rect.right += l[0]
            rect.bottom += l[1]
            rect.top += l[1]

            it.getWindowVisibleDisplayFrame(visibleRect)

            val highBottomBar = resources.getDimension(R.dimen.design_fab_image_size).toInt()
            val bottomInvisibleDiff =
                highBottomBar + resources.getDimension(R.dimen.mole_message_margin_selected).toInt()
            val topInvisibleDiff =
                resources.getDimension(R.dimen.mole_message_margin_selected).toInt()
            visibleRect.bottom -= bottomInvisibleDiff
            visibleRect.top += topInvisibleDiff
            val diff: Int = compareHighRect(visibleRect, rect)

            scrollView?.smoothScrollBy(0, diff, 125)
            val targetScroll = scrollView!!.scrollY + diff
            scrollView?.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY == targetScroll) {
                    scrollView?.isScrollable = false
                    longClickListener.onLongClick(it)
                }
            }

            if (diff == 0) {
                scrollView?.isScrollable = false
                longClickListener.onLongClick(it)
            }
            true
        }
        return view
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
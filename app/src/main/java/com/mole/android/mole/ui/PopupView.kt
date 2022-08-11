package com.mole.android.mole.ui

import android.content.Context
import android.graphics.PixelFormat
import android.os.IBinder
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.PopupWindow


class PopupView constructor(
    contentView: View, width: Int, height: Int, focusable: Boolean = true
) : PopupWindow(contentView, width, height, focusable) {

    private var isDarkInvoked: Boolean = false
    private val darkView: DarkView?
    private val windowManager: WindowManager?

    var selectedView: View?
        set(value) {
            darkView?.selectedView = value
        }
        get() = darkView?.selectedView

    var offsetCutout: Int
        set(value) {
            darkView?.offsetCutout = value
        }
        get() = darkView?.offsetCutout ?: 0

    init {
        windowManager =
            contentView.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        darkView = DarkView(contentView.context)
        darkView.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

    }

    private fun createDarkLayout(token: IBinder): WindowManager.LayoutParams {
        val p = WindowManager.LayoutParams()
        p.gravity = Gravity.START or Gravity.TOP
        p.width = WindowManager.LayoutParams.MATCH_PARENT
        p.height = WindowManager.LayoutParams.MATCH_PARENT
        p.format = PixelFormat.TRANSLUCENT
        p.type = WindowManager.LayoutParams.LAST_SUB_WINDOW
        p.token = token
        p.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED
        return p
    }

    override fun showAsDropDown(anchor: View?, xoff: Int, yoff: Int) {
        invokeBgCover(anchor!!)
        super.showAsDropDown(anchor, xoff, yoff)
    }

    override fun showAtLocation(parent: View?, gravity: Int, x: Int, y: Int) {
        invokeBgCover(parent!!)
        super.showAtLocation(parent, gravity, x, y)
    }

    private fun invokeBgCover(view: View) {
        if (isDarkInvoked || isShowing || contentView == null) {
            return
        }
        if (darkView != null) {
            val darkLP = createDarkLayout(view.windowToken)
            windowManager?.addView(darkView, darkLP)
            isDarkInvoked = true
        }
    }

    override fun dismiss() {
        super.dismiss()
        if (darkView != null && isDarkInvoked) {
            darkView.dismiss()
            windowManager?.removeViewImmediate(darkView)
            isDarkInvoked = false
            darkView.selectedView?.invalidate()
        }
    }

    companion object {
        class Builder {

        }
    }
}
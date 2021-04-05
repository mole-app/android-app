package com.mole.android.mole.ui.mole_bottom_navigation.bottom_navigation_bar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.core.view.iterator
import androidx.core.view.size
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {

    init {
        itemIconTintList = null
        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun inflateMenu(resId: Int) {
        super.inflateMenu(resId)
        menu.size
        for (item in menu) {
            if (item.title == "") {
                item.isEnabled = false
            }
        }

    }
}
package com.mole.android.mole.ui.MoleBottomNavigation.BottomNavigationBar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.google.android.material.bottomnavigation.BottomNavigationView

class MoleBottomNavigationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {

    init {
        itemIconTintList = null
        setBackgroundColor(Color.TRANSPARENT)
    }
}
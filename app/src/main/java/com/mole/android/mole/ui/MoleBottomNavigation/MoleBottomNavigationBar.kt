package com.mole.android.mole.ui.MoleBottomNavigation

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.mole.android.mole.R


class MoleBottomNavigationBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_bottom_app_bar, this)
    }

    private val fab: MaterialButton = findViewById(R.id.backgroundFabView)
    private val navigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.MoleBottomNavigationBar,
                0,
                0
            )
            val (foreground, menu) = try {
                val foreground =
                    typedArray.getDrawable(R.styleable.MoleBottomNavigationBar_foregroundFloatingActionButton)
                val menu = typedArray.getResourceId(
                    R.styleable.MoleBottomNavigationBar_menuBottomNavigation,
                    0
                )
                foreground to menu
            } finally {
                typedArray.recycle()
            }

            handleAttr(foreground, menu)

        }
    }

    private fun handleAttr(foreground: Drawable?, menu: Int) {
        if (foreground != null) {
            setForegroundAttr(foreground)
        }
        if (menu != 0) {
            setMenuAttr(menu)
        }
    }

    private fun setForegroundAttr(foreground: Drawable) {
        fab.foreground = foreground
    }

    private fun setMenuAttr(menu: Int) {
        navigationView.inflateMenu(menu)
    }

    fun setOnFabClickListener(listener: OnClickListener) {
        fab.setOnClickListener(listener)
    }

    fun setOnNavigationItemSelectedListener(listener: BottomNavigationView.OnNavigationItemSelectedListener) {
        navigationView.setOnNavigationItemSelectedListener(listener)
    }

}

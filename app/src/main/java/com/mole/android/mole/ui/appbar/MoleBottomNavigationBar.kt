package com.mole.android.mole.ui.appbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.IdRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.mole.android.mole.R
import com.mole.android.mole.Shape
import com.mole.android.mole.setupBorder


class MoleBottomNavigationBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_bottom_app_bar, this)
    }

    val fab: MaterialButton = findViewById(R.id.background_fab_view)
    private val navigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

    init {
        init(context, attrs)
    }

    fun setSelectedItem(@IdRes itemId: Int) {
        navigationView.selectedItemId = itemId
    }

    fun setOnFabClickListener(listener: OnClickListener) {
        fab.setOnClickListener(listener)
    }

    fun setOnNavigationItemSelectedListener(listener: BottomNavigationView.OnNavigationItemSelectedListener) {
        navigationView.setOnNavigationItemSelectedListener(listener)
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
        fab.setupBorder(Shape.OVAL, 16f)
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

}

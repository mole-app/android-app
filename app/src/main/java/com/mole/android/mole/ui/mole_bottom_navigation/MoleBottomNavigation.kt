package com.mole.android.mole.ui.mole_bottom_navigation

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.mole.android.mole.R


class MoleBottomNavigation @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    private var foregroundFloatingActionButton: Drawable? = null
    private val typeface: Typeface? = null

    init {
        initView()
        init(context, attrs)
    }

    private fun initView() {
        inflate(context, R.layout.view_bottom_app_bar, this)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.MoleBottomNavigation,
                0,
                0
            )

            val N = typedArray.indexCount
            for (i in 0..N) {
                val attr = typedArray.getIndex(i)
                when (attr) {
                    R.styleable.MoleBottomNavigation_foregroundFloatingActionButton -> {
                        val fab: MaterialButton = findViewById(R.id.backgroundFabView)
                        fab.foreground =
                            typedArray.getDrawable(R.styleable.MoleBottomNavigation_foregroundFloatingActionButton)
                    }
                    R.styleable.MoleBottomNavigation_menuBottomNavigation -> {
                        val navigationView: BottomNavigationView =
                            findViewById(R.id.bottomNavigation)
                        navigationView.inflateMenu(
                            typedArray.getResourceId(
                                R.styleable.MoleBottomNavigation_menuBottomNavigation,
                                0
                            )
                        )
                    }
                }
            }

            try {
                foregroundFloatingActionButton =
                    typedArray.getDrawable(R.styleable.MoleBottomNavigation_foregroundFloatingActionButton)
            } finally {
                typedArray.recycle()
            }
        }
    }

    fun setOnClickListenerFAB(listener: OnClickListener) {
        val fab: MaterialButton = findViewById(R.id.backgroundFabView)
        fab.setOnClickListener(listener)
    }

    fun setOnNavigationItemSelectedListener(listener: BottomNavigationView.OnNavigationItemSelectedListener) {
        val navigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        navigationView.setOnNavigationItemSelectedListener(listener)
    }

}

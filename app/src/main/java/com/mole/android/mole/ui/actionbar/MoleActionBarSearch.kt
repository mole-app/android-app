package com.mole.android.mole.ui.actionbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.mole.android.mole.R

class MoleActionBarSearch @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MoleActionBar(context, attrs, defStyleAttr) {

    override fun customView(parent: ViewGroup): View {
        return inflate(context, R.layout.view_action_bar_search, parent)
    }
}
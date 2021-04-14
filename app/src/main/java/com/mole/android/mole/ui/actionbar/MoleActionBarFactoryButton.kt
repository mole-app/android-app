package com.mole.android.mole.ui.actionbar

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.Toolbar
import com.mole.android.mole.R

class MoleActionBarFactoryButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {
    init {
        inflate(context, R.layout.view_action_bar_factory_button, this)
    }


}
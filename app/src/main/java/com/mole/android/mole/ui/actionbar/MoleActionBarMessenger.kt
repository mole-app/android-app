package com.mole.android.mole.ui.actionbar

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.mole.android.mole.R

class MoleActionBarMessenger @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {
    init {
        inflate(context, R.layout.view_action_bar_messenger, this)
    }
    private val nameTextView: TextView = findViewById(R.id.name)

    fun setName(name: String){
        nameTextView.text = name
    }
}
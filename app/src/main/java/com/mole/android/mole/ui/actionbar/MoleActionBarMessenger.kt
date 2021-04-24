package com.mole.android.mole.ui.actionbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mole.android.mole.R

class MoleActionBarMessenger @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MoleActionBar(context, attrs, defStyleAttr) {

    private val nameTextView: TextView = findViewById(R.id.name)

    fun setName(name: String){
        nameTextView.text = name
    }

    override fun customView(parent: ViewGroup): View {
        return inflate(context, R.layout.view_action_bar_messenger, parent)
    }
}
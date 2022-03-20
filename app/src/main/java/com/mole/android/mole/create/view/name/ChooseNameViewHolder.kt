package com.mole.android.mole.create.view.name

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.transition.TransitionManager
import com.google.android.material.textfield.TextInputLayout
import com.mole.android.mole.R
import com.mole.android.mole.auth.view.MoleTextInputLayout
import com.mole.android.mole.create.view.steps.BaseStepsHolder
import androidx.core.content.ContextCompat.getSystemService




class ChooseNameViewHolder(parent: ViewGroup) : BaseStepsHolder(parent, R.layout.holder_choose_name) {

    private var mode = false

    override fun bind() {
        val loginContainer = itemView.findViewById<View>(R.id.login_container)
        val listContainer = itemView.findViewById<View>(R.id.list_container)
        val text = loginContainer.findViewById<TextInputLayout>(R.id.text)
        val clickableArea = loginContainer.findViewById<View>(R.id.clickable_area)
        listContainer.setOnClickListener {
            (itemView as? ViewGroup)?.let { viewGroup ->
                if (!mode) {
                    mode = true
                    TransitionManager.beginDelayedTransition(viewGroup)
                    listContainer.visibility = View.GONE
                    clickableArea.visibility = View.VISIBLE
                    val imm = itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm!!.hideSoftInputFromWindow(itemView.getWindowToken(), 0)
                }
            }
        }

        clickableArea.setOnClickListener {
            (itemView as? ViewGroup)?.let { viewGroup ->
                if (mode) {
                    mode = false
                    TransitionManager.beginDelayedTransition(viewGroup)
                    listContainer.visibility = View.VISIBLE
                    clickableArea.visibility = View.INVISIBLE
                    val imm = itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
                }
            }
        }
        val imm = itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }
}
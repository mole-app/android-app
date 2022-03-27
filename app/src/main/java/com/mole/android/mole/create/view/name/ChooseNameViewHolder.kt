package com.mole.android.mole.create.view.name

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.transition.TransitionManager
import com.google.android.material.textfield.TextInputLayout
import com.mole.android.mole.R
import com.mole.android.mole.create.view.steps.BaseStepsHolder
import androidx.constraintlayout.widget.ConstraintSet

import androidx.constraintlayout.widget.ConstraintLayout

class ChooseNameViewHolder(parent: ViewGroup) : BaseStepsHolder(parent, R.layout.holder_choose_name) {

    private var mode = false

    override fun bind() {
        val loginContainer = itemView.findViewById<View>(R.id.login_container)
        val listContainer = itemView.findViewById<View>(R.id.list_container)
        val text = loginContainer.findViewById<TextInputLayout>(R.id.text)
        val clickableArea = loginContainer.findViewById<View>(R.id.clickable_area)
        listContainer.setOnClickListener {
            (itemView as? ConstraintLayout)?.let { viewGroup ->
                if (!mode) {
                    mode = true
                    TransitionManager.beginDelayedTransition(viewGroup)
                    val constraintLayout = viewGroup
                    val constraintSet = ConstraintSet()
                    constraintSet.clone(constraintLayout)
                    constraintSet.connect(
                        listContainer.id,
                        ConstraintSet.TOP,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.BOTTOM,
                        0
                    )
                    constraintSet.applyTo(constraintLayout)
                    clickableArea.visibility = View.VISIBLE
                    val imm = itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm!!.hideSoftInputFromWindow(itemView.getWindowToken(), 0)
                }
            }
        }

        clickableArea.setOnClickListener {
            (itemView as? ConstraintLayout)?.let { viewGroup ->
                if (mode) {
                    mode = false
                    TransitionManager.beginDelayedTransition(viewGroup)

                    val constraintLayout = viewGroup
                    val constraintSet = ConstraintSet()
                    constraintSet.clone(constraintLayout)
                    constraintSet.connect(
                        listContainer.id,
                        ConstraintSet.TOP,
                        loginContainer.id,
                        ConstraintSet.BOTTOM,
                        0
                    )
                    constraintSet.applyTo(constraintLayout)

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
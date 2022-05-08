package com.mole.android.mole.create.view.steps

import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.create.presentation.ChooseNamePresenter
import com.mole.android.mole.create.presentation.ChooseTagPresenter
import com.mole.android.mole.create.view.amount.ChooseAmountViewHolder
import com.mole.android.mole.create.view.name.ChooseNameViewHolder
import com.mole.android.mole.create.view.tag.ChooseTagHolder
import java.lang.IllegalStateException

class StepsAdapter(
    private val steps: List<Steps>,
    private val scope: LifecycleCoroutineScope,
    private val chooseNamePresenter: ChooseNamePresenter,
    private val chooseTagPresenter: ChooseTagPresenter,
    private val nextClickedListener: (Int) -> Unit,
    ) : RecyclerView.Adapter<BaseStepsHolder>() {

    private var focusable: Focusable? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseStepsHolder {
        return when(viewType) {
            Steps.ChooseName.viewType -> ChooseNameViewHolder(parent, scope, chooseNamePresenter) {
                nextClickedListener(0)
                focusable?.requestFocus()
            }
            Steps.ChooseTag.viewType -> ChooseTagHolder(parent, scope, chooseTagPresenter) {
                nextClickedListener(1)
                focusable?.requestFocus()
            }
            Steps.ChooseAmount.viewType -> ChooseAmountViewHolder(parent)
            else -> throw IllegalStateException("Illegal view type")
        }
    }

    override fun onBindViewHolder(holder: BaseStepsHolder, position: Int) {
        if (position == 0) {
            holder.requestFocus()
        } else {
            focusable = holder
        }
        holder.bind()
    }

    override fun getItemViewType(position: Int): Int {
        return steps[position].viewType
    }

    override fun getItemCount(): Int {
        return steps.size
    }

    interface Focusable {
        fun requestFocus()
    }

}
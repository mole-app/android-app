package com.mole.android.mole.create.view.steps

import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.create.presentation.ChooseAmountPresenter
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
    private val chooseAmountPresenter: ChooseAmountPresenter,
    private val nextClickedListener: (Int, StepResult) -> Unit,
    private val onConfirmCreatingListener: (Int) -> Unit
) : RecyclerView.Adapter<BaseStepsHolder>() {

    sealed class StepResult {
        class UserResult(val id: Int): StepResult()
        class TagResult(val tag: String): StepResult()
    }

    private val holders: MutableList<BaseStepsHolder> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseStepsHolder {
        return when(viewType) {
            Steps.ChooseName.viewType -> ChooseNameViewHolder(parent, scope, chooseNamePresenter) { id ->
                nextClickedListener(0, StepResult.UserResult(id))
                holders.find { it is ChooseTagHolder }?.requestFocus()
            }
            Steps.ChooseTag.viewType -> ChooseTagHolder(parent, scope, chooseTagPresenter) { tag ->
                nextClickedListener(1, StepResult.TagResult(tag))
                holders.find { it is ChooseAmountViewHolder }?.requestFocus()
            }
            Steps.ChooseAmount.viewType -> ChooseAmountViewHolder(parent, scope, chooseAmountPresenter, onConfirmCreatingListener)
            else -> throw IllegalStateException("Illegal view type")
        }.apply {
            holders.add(this)
        }
    }

    override fun onBindViewHolder(holder: BaseStepsHolder, position: Int) {
        if (position == 0 && holder is ChooseTagHolder) {
            holder.requestFocus()
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
package com.mole.android.mole.create.view.steps

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mole.android.mole.create.view.amount.ChooseAmountViewHolder
import com.mole.android.mole.create.view.name.ChooseNameViewHolder
import com.mole.android.mole.create.view.tag.ChooseTagHolder
import java.lang.IllegalStateException

class StepsAdapter(
    private val steps: List<Steps>,
    private val nextClickedListener: (Int) -> Unit
) : RecyclerView.Adapter<BaseStepsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseStepsHolder {
        return when(viewType) {
            Steps.ChooseName.viewType -> ChooseNameViewHolder(parent) { nextClickedListener(0) }
            Steps.ChooseTag.viewType -> ChooseTagHolder(parent)
            Steps.ChooseAmount.viewType -> ChooseAmountViewHolder(parent)
            else -> throw IllegalStateException("Illegal view type")
        }
    }

    override fun onBindViewHolder(holder: BaseStepsHolder, position: Int) {
        holder.bind()
    }

    override fun getItemViewType(position: Int): Int {
        return steps[position].viewType
    }

    override fun getItemCount(): Int {
        return steps.size
    }

}
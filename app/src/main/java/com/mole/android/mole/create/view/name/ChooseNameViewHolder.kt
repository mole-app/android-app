package com.mole.android.mole.create.view.name

import android.view.View
import android.view.ViewGroup
import com.mole.android.mole.R
import com.mole.android.mole.create.view.ChooseTextItemView
import com.mole.android.mole.create.view.steps.BaseStepsHolder

class ChooseNameViewHolder(parent: ViewGroup) : BaseStepsHolder(parent, R.layout.holder_choose_name) {
    override fun bind() {
        val data = (0..20).toList()
        (itemView as? ChooseTextItemView)?.let { chooseItemView ->
            chooseItemView.setDataBinder(
                object : ChooseTextItemView.DataBinder {
                    override val layoutId: Int = R.layout.choose_user_holder
                    override fun itemsCount(): Int = data.size
                    override fun bind(view: View, position: Int) {}
                    override fun contentSame(firstPosition: Int, secondPosition: Int) = false
                    override fun itemSame(firstPosition: Int, secondPosition: Int) = false
                    override fun textForClickedItem(position: Int) = data[position].toString()
                }
            )
        }
    }
}
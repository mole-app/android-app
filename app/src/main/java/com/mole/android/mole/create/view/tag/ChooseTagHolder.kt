package com.mole.android.mole.create.view.tag

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mole.android.mole.R
import com.mole.android.mole.create.model.TagPreview
import com.mole.android.mole.create.view.ChooseTextItemView
import com.mole.android.mole.create.view.steps.BaseStepsHolder

class ChooseTagHolder(parent: ViewGroup, private val nextClickedListener: () -> Unit) : BaseStepsHolder(parent, R.layout.holder_choose_tag) {

    override fun bind() {
        val data = (0..20).map {
            TagPreview(
                name = "какой-то_тег",
                count = it
            )
        }
        (itemView as? ChooseTextItemView)?.let { chooseItemView ->
            chooseItemView.setDataBinder(
                object : ChooseTextItemView.ItemViewContract {
                    override val layoutId: Int = R.layout.choose_tag_item_holder
                    override val titleId: Int = R.string.choose_tag_title
                    override fun itemsCount(): Int = data.size
                    override fun bind(view: View, position: Int) = bindView(view, data[position])
                    override fun contentSame(firstPosition: Int, secondPosition: Int) = false
                    override fun itemSame(firstPosition: Int, secondPosition: Int) = false
                    override fun textForClickedItem(position: Int) = data[position].name
                    override fun onNextClicked() = nextClickedListener()
                    override fun onTextChanged(text: String) {
                    }
                }
            )
        }
    }

    fun bindView(view: View, item: TagPreview) {
        val tag = view.findViewById<TextView>(R.id.tag_name)
        val count = view.findViewById<TextView>(R.id.tag_count)

        tag.text = view.context.getString(R.string.tag_symbol, item.name)
        count.text = view.context.resources.getQuantityString(R.plurals.tag_count, item.count, item.count)
    }
}
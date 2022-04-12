package com.mole.android.mole.create.view.tag

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mole.android.mole.R
import com.mole.android.mole.create.model.TagPreview
import com.mole.android.mole.create.view.ChooseTextItemView
import com.mole.android.mole.create.view.DiffFindItemsViewContract
import com.mole.android.mole.create.view.steps.BaseStepsHolder

class ChooseTagHolder(parent: ViewGroup, private val nextClickedListener: () -> Unit) : BaseStepsHolder(parent, R.layout.holder_choose_tag) {

    private val data = (0..20).map {
        TagPreview(
            name = "${it * 10}",
            count = it
        )
    }

    override fun bind() {
        val newData = data.toMutableList()
        newData.add(0, TagPreview("", 0))
        (itemView as? ChooseTextItemView)?.let { chooseItemView ->
            val contract = object : DiffFindItemsViewContract<TagPreview>(
                chooseItemView,
                this@ChooseTagHolder::itemsSame,
                this@ChooseTagHolder::contentSame,
                this@ChooseTagHolder::bindView,
                TagPreview::name
            ) {
                override val layoutId: Int = R.layout.choose_tag_item_holder
                override val titleId: Int = R.string.choose_tag_title
                override fun onNextClicked() = nextClickedListener()
                override fun onTextChanged(text: String) {
                    val updatedData = data.filter {
                        it.name.contains(text, true)
                    }.toMutableList()

                    val item = data.find { it.name == text }
                    if (item == null) {
                        updatedData.add(0, TagPreview(text, 0))
                    }
                    updateData(updatedData)
                }
            }
            chooseItemView.setDataBinder(contract)
            contract.updateData(newData)
        }
    }

    private fun itemsSame(first: TagPreview, second: TagPreview): Boolean {
        return first.name == second.name
    }

    private fun contentSame(first: TagPreview, second: TagPreview): Boolean {
        return first == second
    }

    private fun bindView(view: View, item: TagPreview) {
        val tag = view.findViewById<TextView>(R.id.tag_name)
        val count = view.findViewById<TextView>(R.id.tag_count)

        tag.text = view.context.getString(R.string.tag_symbol, item.name)
        count.text = view.context.resources.getQuantityString(R.plurals.tag_count, item.count, item.count)
    }
}
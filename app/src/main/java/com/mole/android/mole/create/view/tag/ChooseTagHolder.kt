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

    data class TagPreviewUi(val isNew: Boolean, val preview: TagPreview)

    private val data: List<TagPreviewUi> = (0..20).map {
            TagPreview(
                name = "${it * 10}",
                count = it
            ).toUi()
    }

    init {
        val newData = data.toMutableList()
        newData.add(0, TagPreview("", 0).toUi(true))
        (itemView as? ChooseTextItemView)?.let { chooseItemView ->
            val contract = object : DiffFindItemsViewContract<TagPreviewUi>(
                chooseItemView,
                this@ChooseTagHolder::itemsSame,
                this@ChooseTagHolder::contentSame,
                this@ChooseTagHolder::bindView,
                { it.preview.name }
            ) {
                override val layoutId: Int = R.layout.choose_tag_item_holder
                override val titleId: Int = R.string.choose_tag_title
                override fun onNextClicked() = nextClickedListener()
                override fun onTextChanged(text: String) {
                    val updatedData = data.filter {
                        it.preview.name.contains(text, true)
                    }.toMutableList()

                    val item = data.find { it.preview.name == text }
                    if (item == null) {
                        updatedData.add(0, TagPreview(text, 0).toUi(true))
                    }
                    updateData(updatedData)
                }
            }
            chooseItemView.setDataBinder(contract)
            contract.updateData(newData)
        }
    }

    override fun bind() {

    }

    private fun TagPreview.toUi(isNew: Boolean = false): TagPreviewUi {
        return TagPreviewUi(
            isNew = isNew,
            preview = this
        )
    }

    private fun itemsSame(first: TagPreviewUi, second: TagPreviewUi): Boolean {
        return first.preview.name == second.preview.name || first.isNew && second.isNew
    }

    private fun contentSame(first: TagPreviewUi, second: TagPreviewUi): Boolean {
        return first == second
    }

    private fun bindView(view: View, item: TagPreviewUi) {
        val tag = view.findViewById<TextView>(R.id.tag_name)
        val count = view.findViewById<TextView>(R.id.tag_count)
        val newTag = view.context.getString(R.string.tag_symbol, item.preview.name)
        val newCount = view.context.resources.getQuantityString(R.plurals.tag_count, item.preview.count, item.preview.count)
        if (newTag != tag.text.toString()) {
            tag.text = newTag
        }
        if (newCount != count.text.toString()) {
            count.text = newCount
        }
    }
}
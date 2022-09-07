package com.mole.android.mole.create.view.tag

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import com.mole.android.mole.R
import com.mole.android.mole.create.presentation.ChooseTagPresenter
import com.mole.android.mole.create.view.ChooseTextItemView
import com.mole.android.mole.create.view.DiffFindItemsViewContract
import com.mole.android.mole.create.view.steps.BaseStepsHolder
import com.mole.android.mole.create.view.steps.StepsAdapter
import com.mole.android.mole.create.view.tag.ChooseTagView.TagPreviewUi

class ChooseTagHolder(
    parent: ViewGroup,
    override val scope: LifecycleCoroutineScope,
    private val presenter: ChooseTagPresenter,
    private val nextClickedListener: (String) -> Unit
) : BaseStepsHolder(parent, R.layout.holder_choose_tag), ChooseTagView, StepsAdapter.Focusable {

    private var contract: DiffFindItemsViewContract<TagPreviewUi>? = null
    private val containerView = (itemView as? ChooseTextItemView)

    init {
        containerView?.let(this::bindView)
        containerView?.setOnRetryClickListener { presenter.onRetryClicked() }
    }

    override fun bind() {
        presenter.attachView(this)
    }

    override fun show(data: List<TagPreviewUi>) {
        containerView?.hideProgress()
        contract?.updateData(data)
    }

    override fun showProgress() {
        containerView?.showProgress()
    }

    override fun showError() {
        containerView?.showError()
    }

    override fun showKeyboard() {
        containerView?.focus()
    }

    override fun requestFocus() {
        presenter.onFocusRequested()
    }

    private fun bindView(chooseItemView: ChooseTextItemView) {
        val contract = object : DiffFindItemsViewContract<TagPreviewUi>(
            chooseItemView,
            this@ChooseTagHolder::itemsSame,
            this@ChooseTagHolder::contentSame,
            this@ChooseTagHolder::bindView,
            id = { item -> item.hashCode().toLong() },
            { it.preview.name }
        ) {
            override val layoutId: Int = R.layout.choose_tag_item_holder
            override val titleId: Int = R.string.choose_tag_title
            override fun onSelectedItemNextClicked(item: TagPreviewUi) = nextClickedListener(item.preview.name)
            override fun onTextChanged(text: String) {
                presenter.onInputChange(text)
            }
        }
        chooseItemView.setDataBinder(contract)
        this.contract = contract
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
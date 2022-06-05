package com.mole.android.mole.create.view.name

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleCoroutineScope
import coil.load
import coil.transform.CircleCropTransformation
import com.mole.android.mole.R
import com.mole.android.mole.create.model.UserPreview
import com.mole.android.mole.create.presentation.ChooseNamePresenter
import com.mole.android.mole.create.view.ChooseTextItemView
import com.mole.android.mole.create.view.DiffFindItemsViewContract
import com.mole.android.mole.create.view.steps.BaseStepsHolder
import com.mole.android.mole.setHighLightedText


class ChooseNameViewHolder(
    parent: ViewGroup,
    override val scope: LifecycleCoroutineScope,
    private val presenter: ChooseNamePresenter,
    private val nextClickedListener: () -> Unit
) :
    BaseStepsHolder(parent, R.layout.holder_choose_name), ChooseNameView {

    private var contract: DiffFindItemsViewContract<ChooseNameView.UserPreviewUi>? = null
    private val itemsContainer = itemView as? ChooseTextItemView

    init {
        itemsContainer?.let(this::bindItemView)
        itemsContainer?.setOnRetryClickListener { presenter.onRetryClicked() }
    }

    override fun bind() {
        presenter.attachView(this)
    }

    override fun requestFocus() {
        // NOTHING TO FOCUS
    }

    override fun showKeyboard() {
        itemsContainer?.focus()
    }

    override fun show(data: List<ChooseNameView.UserPreviewUi>) {
        itemsContainer?.hideProgress()
        contract?.updateData(data)
    }

    override fun showProgress() {
        itemsContainer?.showProgress()
    }

    override fun showError() {
        itemsContainer?.showError()
    }

    private fun bindItemView(view: ChooseTextItemView) {
        val contract = object : DiffFindItemsViewContract<ChooseNameView.UserPreviewUi>(
            view,
            this@ChooseNameViewHolder::itemsSame,
            this@ChooseNameViewHolder::contentSame,
            this@ChooseNameViewHolder::bindView,
            { it.userPreview.login }
        ) {
            override val layoutId: Int = R.layout.choose_user_holder
            override val titleId: Int = R.string.choose_login_title
            override fun onNextClicked() = nextClickedListener()
            override fun onTextChanged(text: String) = presenter.onInputChange(text)
        }
        view.setDataBinder(contract)
        this.contract = contract
    }

    private fun itemsSame(first: ChooseNameView.UserPreviewUi, second: ChooseNameView.UserPreviewUi): Boolean {
        return first.userPreview.id == second.userPreview.id
    }

    private fun contentSame(first: ChooseNameView.UserPreviewUi, second: ChooseNameView.UserPreviewUi): Boolean {
        return first == second
    }

    private fun bindView(view: View, item: ChooseNameView.UserPreviewUi) {
        val login = view.findViewById<TextView>(R.id.user_login)
        val name = view.findViewById<TextView>(R.id.user_name)
        val avatar = view.findViewById<AppCompatImageView>(R.id.user_icon)
        val uri = item.userPreview.avatar.photoSmall
        val color = ContextCompat.getColor(view.context, R.color.color_accent)

        name.text = item.userPreview.name
        login.text = item.userPreview.login

        name.setHighLightedText(item.highlightFilter, color)
        login.setHighLightedText(item.highlightFilter, color)
        if (uri.isNotBlank()) {
            avatar.load(uri) {
                transformations(CircleCropTransformation())
            }
        } else {
            avatar.load(R.drawable.ic_not_avatar_foreground) {
                transformations(CircleCropTransformation())
            }
        }
    }

}
package com.mole.android.mole.create.view.name

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import coil.load
import coil.transform.CircleCropTransformation
import com.mole.android.mole.R
import com.mole.android.mole.create.model.UserPreview
import com.mole.android.mole.create.view.ChooseTextItemView
import com.mole.android.mole.create.view.DiffFindItemsViewContract
import com.mole.android.mole.create.view.steps.BaseStepsHolder
import com.mole.android.mole.setHighLightedText


class ChooseNameViewHolder(parent: ViewGroup, private val nextClickedListener: () -> Unit) :
    BaseStepsHolder(parent, R.layout.holder_choose_name) {
    override fun bind() {
        val data = usersTestData.map {
            UserPreviewUi(it, "")
        }
        (itemView as? ChooseTextItemView)?.let { chooseItemView ->
            val contract = object : DiffFindItemsViewContract<UserPreviewUi>(
                chooseItemView,
                this@ChooseNameViewHolder::itemsSame,
                this@ChooseNameViewHolder::contentSame,
                this@ChooseNameViewHolder::bindView,
                { it.userPreview.login }
            ) {
                override val layoutId: Int = R.layout.choose_user_holder
                override val titleId: Int = R.string.choose_login_title
                override fun onNextClicked() = nextClickedListener()
                override fun onTextChanged(text: String) {
                    val updatedData = data.filter {
                        it.userPreview.name.contains(text, true) ||
                        it.userPreview.login.contains(text, true)
                    }
                        .map {  UserPreviewUi(it.userPreview, text) }
                        .toMutableList()
                    updateData(updatedData)
                }
            }
            chooseItemView.setDataBinder(contract)
            contract.updateData(data)
        }
    }

    override fun requestFocus() {
        (itemView as? ChooseTextItemView)?.focus()
    }

    private fun itemsSame(first: UserPreviewUi, second: UserPreviewUi): Boolean {
        return first.userPreview.id == second.userPreview.id
    }

    private fun contentSame(first: UserPreviewUi, second: UserPreviewUi): Boolean {
        return first == second
    }

    private fun bindView(view: View, item: UserPreviewUi) {
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

    private data class UserPreviewUi(val userPreview: UserPreview, val highlightFilter: String)

}
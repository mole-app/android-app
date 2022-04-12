package com.mole.android.mole.create.view.name

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import coil.load
import coil.transform.CircleCropTransformation
import com.mole.android.mole.R
import com.mole.android.mole.create.model.UserPreview
import com.mole.android.mole.create.view.ChooseTextItemView
import com.mole.android.mole.create.view.DiffFindItemsViewContract
import com.mole.android.mole.create.view.steps.BaseStepsHolder

class ChooseNameViewHolder(parent: ViewGroup, private val nextClickedListener: () -> Unit) :
    BaseStepsHolder(parent, R.layout.holder_choose_name) {
    override fun bind() {
        val data = usersTestData
        (itemView as? ChooseTextItemView)?.let { chooseItemView ->
            val contract = object : DiffFindItemsViewContract<UserPreview>(
                chooseItemView,
                this@ChooseNameViewHolder::itemsSame,
                this@ChooseNameViewHolder::contentSame,
                this@ChooseNameViewHolder::bindView,
                UserPreview::login
            ) {
                override val layoutId: Int = R.layout.choose_user_holder
                override val titleId: Int = R.string.choose_login_title
                override fun onNextClicked() = nextClickedListener()
                override fun onTextChanged(text: String) {}
            }
            chooseItemView.setDataBinder(contract)
            contract.updateData(data)
        }
    }

    private fun itemsSame(first: UserPreview, second: UserPreview): Boolean {
        return first.id == second.id
    }

    private fun contentSame(first: UserPreview, second: UserPreview): Boolean {
        return first == second
    }

    fun bindView(view: View, item: UserPreview) {
        val login = view.findViewById<TextView>(R.id.user_login)
        val name = view.findViewById<TextView>(R.id.user_name)
        val avatar = view.findViewById<AppCompatImageView>(R.id.user_icon)
        val uri = item.avatar.photoSmall

        name.text = item.name
        login.text = item.login
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
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
import com.mole.android.mole.create.view.steps.BaseStepsHolder
import com.mole.android.mole.profile.data.ProfilePhoto

class ChooseNameViewHolder(parent: ViewGroup, private val nextClickedListener: () -> Unit) :
    BaseStepsHolder(parent, R.layout.holder_choose_name) {
    override fun bind() {
        val data = (0..20).map {
            UserPreview(
                id = it,
                name = "Александр",
                login = "@sanya666",
                avatar = ProfilePhoto(
                    photoSmall = "https://sun9-76.userapi.com/s/v1/if1/4DnU91gHpDZ6PbM4AbZovkgJy8ERfUI8kfVzo8l2qeigba-yNU1trkXmncZTrCC9nyzU67hu.jpg?size=200x200&quality=96&crop=381,67,840,840&ava=1",
                    photoNormal = "https://sun9-76.userapi.com/s/v1/if1/4DnU91gHpDZ6PbM4AbZovkgJy8ERfUI8kfVzo8l2qeigba-yNU1trkXmncZTrCC9nyzU67hu.jpg?size=200x200&quality=96&crop=381,67,840,840&ava=1"
                )
            )
        }
        (itemView as? ChooseTextItemView)?.let { chooseItemView ->
            chooseItemView.setDataBinder(
                object : ChooseTextItemView.ItemViewContract {
                    override val layoutId: Int = R.layout.choose_user_holder
                    override val titleId: Int = R.string.choose_login_title
                    override fun itemsCount(): Int = data.size
                    override fun bind(view: View, position: Int) = bindView(view, data[position])
                    override fun contentSame(firstPosition: Int, secondPosition: Int) = false
                    override fun itemSame(firstPosition: Int, secondPosition: Int) = false
                    override fun textForClickedItem(position: Int) = data[position].login
                    override fun onNextClicked() = nextClickedListener()
                    override fun onTextChanged(text: String) {}
                }
            )
        }
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
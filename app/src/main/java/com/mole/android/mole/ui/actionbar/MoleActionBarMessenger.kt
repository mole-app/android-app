package com.mole.android.mole.ui.actionbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.load
import coil.transform.CircleCropTransformation
import com.mole.android.mole.R
import com.mole.android.mole.ui.MoleMessageView

class MoleActionBarMessenger @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MoleActionBar(context, attrs, defStyleAttr) {

    private val avatarImageView: ImageView = findViewById(R.id.avatar)
    private val nameTextView: TextView = findViewById(R.id.name)
    private val balanceBarMoleMessageView: MoleMessageView = findViewById(R.id.balance_bar)

    fun setName(name: String) {
        nameTextView.text = name
    }

    fun setAvatar(avatarUrl: String) {
        avatarImageView.load(avatarUrl) {
            error(R.drawable.ic_not_avatar_foreground)
            transformations(CircleCropTransformation())
        }
    }

    fun setBalance(balance: Int) {
        balanceBarMoleMessageView.balance = balance
    }

    override fun customView(parent: ViewGroup): View {
        return inflate(context, R.layout.view_action_bar_messenger, parent)
    }
}
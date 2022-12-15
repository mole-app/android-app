package com.mole.android.mole.debts.view.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.mole.android.mole.MoleBinder
import com.mole.android.mole.PopupProvider
import com.mole.android.mole.R
import com.mole.android.mole.component
import com.mole.android.mole.databinding.ItemDebtsViewBinding
import com.mole.android.mole.debts.data.DebtorData
import com.mole.android.mole.debts.view.OnItemDebtsClickListener

class DebtsDebtorViewHolder(
    private val binding: ItemDebtsViewBinding,
    private val onItemClickListener: OnItemDebtsClickListener,
    private val popupProvider: PopupProvider<DebtorData>? = null
) : RecyclerView.ViewHolder(binding.root), MoleBinder<DebtorData>,
    View.OnLongClickListener {

    private var data: DebtorData? = null

    init {
        if (popupProvider != null) {
            with(binding.itemChatView) {
                setOnTouchListener(popupProvider.touchListener)
            }
        }
    }

    override fun bind(data: DebtorData) {
        binding.personName.text = data.name
        binding.personDebtsTotal.balance = data.debtsSum
        binding.personDebtsCount.text = component().context.resources.getQuantityString(
            R.plurals.debts_plurals,
            data.debtsCount,
            data.debtsCount
        )
        binding.personIcon.load(data.imageUrl) {
            error(R.drawable.ic_not_avatar_foreground)
            transformations(CircleCropTransformation())
        }
        with(binding.itemChatView) {
            setOnClickListener {
                onItemClickListener.onShotClick(data)
            }

            if (data.debtsSum == 0) {
                setOnLongClickListener(null)
            } else {
                setOnLongClickListener(this@DebtsDebtorViewHolder)
            }
        }
        this.data = data
    }

    override fun onLongClick(view: View): Boolean {
        return data?.run {
            onItemClickListener.onLongClick(view, this)
            true
        } ?: false
    }
}

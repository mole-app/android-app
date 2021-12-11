package com.mole.android.mole.debts.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.mole.android.mole.R
import com.mole.android.mole.component
import com.mole.android.mole.databinding.ItemChatTitleViewBinding
import com.mole.android.mole.databinding.ItemChatViewBinding
import com.mole.android.mole.debts.data.DebtsData

class DebtsViewAdapter(
    private val onItemChatClickListener: OnItemChatClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var chatsData: List<DebtsData>

    fun setData(data: List<DebtsData>) {
        chatsData = data
    }

    fun getData(): List<DebtsData> {
        return chatsData
    }

    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_CHAT = 2
    }

    inner class ChatViewHolder(view: View) : BaseViewHolder<DebtsData.ChatDebtsData>(view) {
        override fun bind(data: DebtsData.ChatDebtsData) {
            ItemChatViewBinding.bind(itemView).apply {
                personName.text = data.personName
                personDebtsCount.text = component().context.resources.getQuantityString(
                    R.plurals.debts_plurals,
                    data.personDebtsCount,
                    data.personDebtsCount
                )
                personDebtsTotal.balance = data.personDebtsTotal
                personIcon.load(R.drawable.test_image) {
                    transformations(CircleCropTransformation())
                }
                itemChatView.setOnLongClickListener {
                    onItemChatClickListener.onLongClick(it, data)
                    true
                }
                itemChatView.setOnClickListener {
                    onItemChatClickListener.onShotClick(data)
                }
            }
        }
    }

    inner class TitleViewHolder(view: View) : BaseViewHolder<DebtsData>(view) {
        override fun bind(data: DebtsData) {
            data as DebtsData.TotalDebtsData
            ItemChatTitleViewBinding.bind(itemView).apply {
                titleChatTextView.text =
                    component().context.resources.getString(R.string.total_debts, data.debtsTotal)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER
        else TYPE_CHAT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_CHAT -> {
                ChatViewHolder(parent)
                val binding =
                    ItemChatViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ChatViewHolder(binding.root)
            }
            else -> {
                val binding =
                    ItemChatTitleViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                TitleViewHolder(binding.root)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = chatsData[position]
        return when (item) {
            is DebtsData.ChatDebtsData -> (holder as? ChatViewHolder)?.bind(item)
            is DebtsData.TotalDebtsData -> (holder as? TitleViewHolder)?.bind(item)
        } ?: Unit
    }

    override fun getItemCount() = chatsData.size
}
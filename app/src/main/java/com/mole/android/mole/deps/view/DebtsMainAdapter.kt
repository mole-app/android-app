package com.mole.android.mole.deps.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mole.android.mole.R
import com.mole.android.mole.databinding.ItemChatTitleViewBinding
import com.mole.android.mole.databinding.ItemChatViewBinding
import com.mole.android.mole.deps.data.ChatData
import com.mole.android.mole.ui.CircleImageTransformation

class DebtsMainAdapter : RecyclerView.Adapter<DebtsMainAdapter.BaseViewHolder>() {

    private lateinit var chatsData: List<ChatData>

    fun setData(data: List<ChatData>) {
        chatsData = data
        notifyDataSetChanged()
    }

    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_CHAT = 2
    }

    abstract inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(data: List<ChatData>, position: Int)
    }

    inner class ChatViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: List<ChatData>, position: Int) {
            ItemChatViewBinding.bind(itemView).apply {
                userName.text = data[position].userName
                userDebtsCount.text = data[position].userDebtsCount
                userIcon.load(R.drawable.test_image) {
                    transformations(CircleImageTransformation())
                }
                userDebtsTotal.balance = data[position].userTotalDebts
            }
        }
    }

    inner class TitleViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: List<ChatData>, position: Int) {
            var totalDebts : Int = 0
            for (chatData in data) {
                totalDebts += chatData.userTotalDebts
            }
            ItemChatTitleViewBinding.bind(itemView).titleChatTextView.text = "Всего: ${totalDebts.toString()} р."
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER
        else TYPE_CHAT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when(viewType){
            TYPE_CHAT -> {
                val binding =
                    ItemChatViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ChatViewHolder(binding.root)
            }
            else -> {
                val binding =
                    ItemChatTitleViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TitleViewHolder(binding.root)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(chatsData, position)
    }

    override fun getItemCount() = chatsData.size
}
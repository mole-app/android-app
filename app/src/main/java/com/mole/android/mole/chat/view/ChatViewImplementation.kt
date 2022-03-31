package com.mole.android.mole.chat.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.component
import com.mole.android.mole.databinding.FragmentChatBinding

class ChatViewImplementation :
    MoleBaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate), ChatView {

    private val presenter = component().chatModule.chatPresenter
    private val chatAdapter = ChatAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.chatRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        binding.chatRecyclerView.adapter = chatAdapter
        chatAdapter.setData(presenter.getData())
        chatAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun setData(data: List<ChatData>) {
        chatAdapter.setData(data)
        chatAdapter.notifyDataSetChanged()
    }
}
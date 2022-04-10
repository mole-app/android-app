package com.mole.android.mole.chat.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.R
import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.component
import com.mole.android.mole.databinding.FragmentChatBinding
import com.mole.android.mole.ui.actionbar.MoleActionBar

class ChatViewImplementation :
    MoleBaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate), ChatView {

    companion object {
        private const val ARG_NAME = "name"
        private const val ARG_TOTAL_DEBTS = "total_debts"
        private const val ARG_AVATAR_URL = "avatar_url"
        fun newInstance(name: String, totalDebts: Int, avatarUrl: String?): ChatViewImplementation {
            val args = Bundle()
            val fragment = ChatViewImplementation()
            args.putString(ARG_NAME, name)
            args.putInt(ARG_TOTAL_DEBTS, totalDebts)
            args.putString(ARG_AVATAR_URL, avatarUrl)
            fragment.arguments = args
            return fragment
        }
    }

    private val presenter = component().chatModule.chatPresenter
    private val chatAdapter = ChatAdapter()


    override fun getNavigator(): Navigator {
        return AppNavigator(requireActivity(), R.id.fragment_container)
    }

    override fun getToolbar(): MoleActionBar {
        return binding.chatToolbarMessenger
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        initToolbar()
        initRecyclerView()
    }

    private fun initToolbar() {
        val name = arguments?.getString(ARG_NAME)
        val totalDebts = arguments?.getInt(ARG_TOTAL_DEBTS)
        val avatarUrl = arguments?.getString(ARG_AVATAR_URL)
        with(binding.chatToolbarMessenger){
            setName(name)
            setBalance(totalDebts)
            setAvatar(avatarUrl)
        }
    }

    private fun initRecyclerView() {
        binding.chatRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        binding.chatRecyclerView.adapter = chatAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun setData(data: List<ChatData>) {
        chatAdapter.setData(data)
        chatAdapter.notifyDataSetChanged()

    }

    override fun showLoading() {
        binding.chatProgressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.chatProgressBar.visibility = View.GONE
    }
}
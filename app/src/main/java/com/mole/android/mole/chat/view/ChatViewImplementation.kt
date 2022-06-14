package com.mole.android.mole.chat.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.PopupProvider
import com.mole.android.mole.R
import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.component
import com.mole.android.mole.create.view.CreateDebtScreen
import com.mole.android.mole.databinding.FragmentChatBinding
import com.mole.android.mole.ui.MoleMessageViewWithInfo
import com.mole.android.mole.setResultListenerGeneric
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
    private val chatAdapter by lazy { ChatAdapter(popupProvider) }
    private lateinit var popupProvider: PopupProvider<Int>
    private val itemCountBeforeListScrollToTop = 10
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager: LinearLayoutManager? =
                recyclerView.layoutManager as? LinearLayoutManager
            val totalItemCount = layoutManager?.itemCount ?: 0
            val lastVisibleItemPosition = layoutManager?.findLastVisibleItemPosition() ?: 0
            if (lastVisibleItemPosition + 1 == totalItemCount) {
                presenter.onChatScrolledToTop()
            }
            else if (lastVisibleItemPosition + 1 + itemCountBeforeListScrollToTop >= totalItemCount) {
                presenter.onChatPreScrolledToTop()
            }
        }
    }
    private val router = component().routingModule.router

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
        initChatFabView()
        popupProvider = PopupProvider(requireContext(), binding.chatRecyclerView, view)

        popupProvider.setOnDeleteListener { deletedView, id ->
            presenter.onDeleteItem(id)
            val chatItem = (deletedView as? MoleMessageViewWithInfo)
            chatItem?.apply {
                setDisabled()
            }
        }

        initRecyclerView()
    }

    private fun initToolbar() {
        val name = arguments?.getString(ARG_NAME) as String
        val totalDebts = arguments?.getInt(ARG_TOTAL_DEBTS) as Int
        val avatarUrl = arguments?.getString(ARG_AVATAR_URL) as String
        with(binding.chatToolbarMessenger) {
            setName(name)
            setBalance(totalDebts)
            setAvatar(avatarUrl)
        }
    }

    private fun initChatFabView() {
        binding.chatFabView.setOnClickListener {
            router.navigateTo(FragmentScreen { CreateDebtScreen.instance(1) })
            router.setResultListenerGeneric<CreateDebtScreen.CreatedDebt>(CreateDebtScreen.EXTRA_CREATED_DEBT) {
                Toast.makeText(context, "$it", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initRecyclerView() {
        binding.chatRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        binding.chatRecyclerView.adapter = chatAdapter
        binding.chatRecyclerView.addItemDecoration(ChatFirstPositionItemDecoration())
        binding.chatRecyclerView.addOnScrollListener(scrollListener)
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
package com.mole.android.mole.chat.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.R
import com.mole.android.mole.chat.data.ChatDataDebtorDomain
import com.mole.android.mole.chat.data.ChatDebtsDataUi
import com.mole.android.mole.component
import com.mole.android.mole.create.view.CreateDebtScreen
import com.mole.android.mole.databinding.FragmentChatBinding
import com.mole.android.mole.setResultListenerGeneric
import com.mole.android.mole.ui.actionbar.MoleActionBar

class ChatViewImplementation :
    MoleBaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate), ChatView {

    companion object {
        private const val ARG_USER_ID = "user_id"
        fun newInstance(userId: Int): ChatViewImplementation {
            val args = Bundle()
            val fragment = ChatViewImplementation()
            args.putInt(ARG_USER_ID, userId)
            fragment.arguments = args
            return fragment
        }
    }

    private val presenter by lazy {
        val userId = arguments?.getInt(ARG_USER_ID) ?: 0
        component().chatModule.chatPresenter(userId)
    }
    private lateinit var chatAdapter: ChatAdapter
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
            } else if (lastVisibleItemPosition + 1 + itemCountBeforeListScrollToTop >= totalItemCount) {
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
        chatAdapter = ChatAdapter()
        initChatFabView()
        initRecyclerView()
    }

    private fun initChatFabView() {
        binding.chatFabView.setOnClickListener {
            presenter.onFabViewClicked()
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

    override fun setData(data: List<ChatDebtsDataUi>) {
//        val diffUtilCallback = ChatDiffUtilCallback(chatAdapter.getData(), data)
//        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
        chatAdapter.addAll(data)
        chatAdapter.notifyDataSetChanged()
//        diffUtilResult.dispatchUpdatesTo(chatAdapter)

    }

    override fun setToolbarData(data: ChatDataDebtorDomain) {
        with(binding.chatToolbarMessenger) {
            setName(data.name)
            setBalance(data.balance)
            setAvatar(data.avatarUrl)
        }
    }

    override fun setToolbarLoading() {
        with(binding.chatToolbarMessenger) {
            setName("...")
            setBalance(0)
            setAvatar("")
        }
    }

    override fun showLoading() {
        binding.chatProgressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.chatProgressBar.visibility = View.GONE
    }

    override fun showCreateDebtScreen(userId: Int) {
        router.navigateTo(FragmentScreen { CreateDebtScreen.instance(userId) })
        router.setResultListenerGeneric<CreateDebtScreen.CreatedDebt>(CreateDebtScreen.EXTRA_CREATED_DEBT) {
        }
    }
}
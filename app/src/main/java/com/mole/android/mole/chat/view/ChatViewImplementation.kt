package com.mole.android.mole.chat.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.mole.android.mole.*
import com.mole.android.mole.chat.data.ChatDataDebtorDomain
import com.mole.android.mole.chat.data.ChatDebtsDataUi
import com.mole.android.mole.create.view.CreateDebtScreen
import com.mole.android.mole.databinding.FragmentChatBinding
import com.mole.android.mole.navigation.Screens
import com.mole.android.mole.repay.data.RepayData
import com.mole.android.mole.ui.MoleMessageViewWithInfo
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

    private lateinit var popupProvider: PopupProvider<Int>
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
    private val retryListener = object : RetryButtonClickListener {
        override fun onRetryButtonClicked() {
            presenter.onRetryClick()
        }
    }

    override fun getNavigator(): Navigator {
        return AppNavigator(requireActivity(), R.id.fragment_container)
    }

    override fun getToolbar(): MoleActionBar {
        return binding.chatToolbarMessenger
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        popupProvider = PopupProvider(
            requireContext(),
            binding.chatRecyclerView,
            view,
            isEditDisable = true,
            isBalanceDisable =  true)
        chatAdapter = ChatAdapter(popupProvider, retryListener)
        initChatFabView()
        initRetryButton()

        popupProvider.setOnDeleteListener { deletedView, id ->
            presenter.onDeleteItem(id)
            val chatItem = (deletedView as? MoleMessageViewWithInfo)
            chatItem?.apply {
                isDeleted = true
                binding.chatToolbarMessenger.addToBalance(-balance)
            }
        }

        initRecyclerView()
        initToolbar()
    }

    private fun initToolbar() {
        binding.chatToolbarMessenger.setOnClickListener {
            presenter.onChatToolbarClicked()
        }
    }

    private fun initRetryButton() {
        binding.retryButton.setOnClickListener {
            binding.retryButton.isEnabled = false
            binding.retryButton.visibility = View.GONE
            presenter.onRetryClick()
        }
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

    private fun hideContent() {
        binding.chatRecyclerView.visibility = View.GONE
        binding.chatFabView.visibility = View.GONE
    }

    private fun showContent() {
        binding.chatRecyclerView.visibility = View.VISIBLE
        binding.chatFabView.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun setData(data: List<ChatDebtsDataUi>) {
        val newData = chatAdapter.getData() + data
        val diffUtilCallback = ChatDiffUtilCallback(chatAdapter.getData(), newData)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
        chatAdapter.addAll(data)
        diffUtilResult.dispatchUpdatesTo(chatAdapter)
    }

    override fun setToolbarData(data: ChatDataDebtorDomain) {
        with(binding.chatToolbarMessenger) {
            setName(data.name)
            setBalance(data.balance)
            setAvatar(data.avatarUrl.urlSmall)
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

    override fun showErrorToast() {
        Toast.makeText(context, resources.getString(R.string.loading_error), Toast.LENGTH_LONG)
            .show()
    }

    override fun showError() {
        hideContent()
        binding.chatToolbarMessenger.setVisibilityToBalance(false)
        binding.chatToolbarMessenger.setName(resources.getString(R.string.loading_error))
        binding.retryButton.isEnabled = true
        binding.retryButton.visibility = View.VISIBLE
    }

    override fun hideError(isRetryBtnInRecyclerView: Boolean) {
        showContent()
        binding.chatToolbarMessenger.setVisibilityToBalance(true)
        if (isRetryBtnInRecyclerView) {
            val position = chatAdapter.getData().lastIndex
            chatAdapter.deleteData(position)
            chatAdapter.notifyItemRemoved(position)
        }
    }

    override fun showRepayScreen(data: ChatDataDebtorDomain) {
        router.navigateTo(
            Screens.Repay(
                RepayData(
                    userId = data.id,
                    userName = data.name,
                    userIconUrl = data.avatarUrl.urlNormal,
                    allDebtsSum = data.balance
                ),
                openChat = true
            )
        )
    }
}
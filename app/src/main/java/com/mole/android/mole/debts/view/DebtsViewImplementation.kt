package com.mole.android.mole.debts.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mole.android.mole.*
import com.mole.android.mole.databinding.FragmentDebtsBinding
import com.mole.android.mole.debts.data.DebtorData
import com.mole.android.mole.debts.data.DebtsData
import com.mole.android.mole.navigation.Screens
import com.mole.android.mole.repay.data.RepayData
import com.mole.android.mole.ui.actionbar.MoleActionBar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DebtsViewImplementation :
    MoleBaseFragment<FragmentDebtsBinding>(FragmentDebtsBinding::inflate), DebtsView {

    private lateinit var popupProvider: PopupProvider<DebtorData>
    private val presenter = component().debtsModule.debtsPresenter
    private val listener = object : OnItemDebtsClickListener {
        override fun onLongClick(view: View, chatData: DebtorData) {
            popupProvider.start(view, chatData, PopupProvider.Position.RIGHT)
        }

        override fun onShotClick(chatData: DebtorData) {
            presenter.onItemShortClick(chatData.id)
        }
    }
    private lateinit var debtsAdapter: DebtsAdapter

    override fun getNavigator() = AppNavigator(requireActivity(), R.id.nav_host_fragment)

    override fun getToolbar(): MoleActionBar {
        return binding.actionBar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        popupProvider = PopupProvider(
            requireContext(),
            binding.debtsRecyclerView,
            view,
            requireContext().resolveColor(R.attr.colorAccent),
            isEditDisable = true,
            isDeleteDisable = true
        )
        debtsAdapter = DebtsAdapter(popupProvider, listener)

        initRecyclerView()
        initErrorView()

        popupProvider.setOnBalanceListener { _, debtorData ->
            presenter.onBalanceItem(debtorData)
        }

        lifecycleScope.launch {
            presenter.state.collect { state ->
                showLoading(state.isLoading)
                showContent(state.isContentVisible, state.content)
                showError(state.isErrorVisible)
            }
        }
    }

    private fun initErrorView() {
        binding.errorView.hideView()
        binding.errorView.setRetryClickListener {
            presenter.onRetryClick()
            binding.loading.visibility = View.VISIBLE
        }
    }

    private fun initRecyclerView() {
        with(binding.debtsRecyclerView) {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = debtsAdapter
            addItemDecoration(DebtsLastPositionItemDecoration())
        }
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    private fun setData(data: DebtsData) {
        binding.totalDebtsSum.text = component().context.resources.getString(
            R.string.total_debts,
            summaryToString(data.debtsSumTotal.toLong())
        )
        val diffUtilCallback = DebtsDiffUtilCallback(debtsAdapter.getData(), data.debtors)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
        debtsAdapter.update(data.debtors)
        diffUtilResult.dispatchUpdatesTo(debtsAdapter)
    }

    private fun showLoading(isVisible: Boolean) {
        binding.loading.isVisible = isVisible
        if (isVisible) {
            binding.totalDebtsSum.text = component().context.resources.getString(
                R.string.total_debts,
                "..."
            )
        }
    }

    private fun showContent(isVisible: Boolean, data: DebtsData?) {
        with(binding) {
            debtsRecyclerView.isVisible = isVisible
            totalDebtsSum.isVisible = isVisible
        }
        if (data != null) {
            setData(data)
        }
    }

    private fun showError(isVisible: Boolean) {
        if (isVisible) binding.errorView.showView()
        else binding.errorView.hideView()
    }

    override fun showRepayScreen(data: DebtorData) {
        component().routingModule.navigationHolder.setNavigator(
            AppNavigator(
                requireActivity(),
                R.id.fragment_container
            )
        )
        component().routingModule.router.navigateTo(
            Screens.Repay(
                RepayData(
                    userId = data.id,
                    userName = data.name,
                    userIconUrl = data.imageUrl,
                    allDebtsSum = data.debtsSum
                )
            )
        )
    }

    override fun showChatScreen(idDebtor: Int) {
        component().routingModule.navigationHolder.setNavigator(
            AppNavigator(
                requireActivity(),
                R.id.fragment_container
            )
        )
        component().routingModule.router.navigateTo(Screens.Chat(idDebtor))
    }
}

package com.mole.android.mole.debts.view

import android.os.Bundle
import android.view.View
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

    override fun setData(data: DebtsData) {
        showContent()
        binding.totalDebtsSum.text = component().context.resources.getString(
            R.string.total_debts,
            summaryToString(data.debtsSumTotal.toLong())
        )
        val diffUtilCallback = DebtsDiffUtilCallback(debtsAdapter.getData(), data.debtors)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
        debtsAdapter.update(data.debtors)
        diffUtilResult.dispatchUpdatesTo(debtsAdapter)
    }

    override fun showLoading() {
        binding.loading.visibility = View.VISIBLE
        binding.totalDebtsSum.text = component().context.resources.getString(
            R.string.total_debts,
            "..."
        )
    }

    override fun hideLoading() {
        binding.loading.visibility = View.GONE
    }

    override fun showError(code: Int, description: String) {
        hideContent()
        binding.errorView.showView()
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

    private fun hideContent() {
        with(binding) {
            debtsRecyclerView.visibility = View.GONE
            loading.visibility = View.GONE
            totalDebtsSum.visibility = View.GONE
        }
    }

    private fun showContent() {
        with(binding) {
            errorView.hideView()
            debtsRecyclerView.visibility = View.VISIBLE
            loading.visibility = View.VISIBLE
            totalDebtsSum.visibility = View.VISIBLE
        }
    }
}

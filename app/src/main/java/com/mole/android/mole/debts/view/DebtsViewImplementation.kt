package com.mole.android.mole.debts.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.R
import com.mole.android.mole.component
import com.mole.android.mole.databinding.FragmentDebtsBinding
import com.mole.android.mole.debts.data.DebtorData
import com.mole.android.mole.debts.data.DebtsData
import com.mole.android.mole.navigation.Screens
import com.mole.android.mole.summaryToString
import com.mole.android.mole.ui.actionbar.MoleActionBar

class DebtsViewImplementation :
    MoleBaseFragment<FragmentDebtsBinding>(FragmentDebtsBinding::inflate), DebtsView {

    private val presenter = component().debtsModule.debtsPresenter
    private val listener = object : OnItemDebtsClickListener {
        override fun onLongClick(view: View, chatData: DebtorData) {
            presenter.onItemLongClick()
        }

        override fun onShotClick(chatData: DebtorData) {
            presenter.onItemShortClick(chatData.id)
        }
    }
    private val debtsAdapter = DebtsAdapter(listener)

    override fun getNavigator() = AppNavigator(requireActivity(), R.id.nav_host_fragment)

    override fun getToolbar(): MoleActionBar {
        return binding.actionBar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        initRecyclerView()
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
        super.onDestroyView()
        presenter.detachView()
    }

    override fun setData(data: DebtsData) {
        binding.totalDebtsSum.text = component().context.resources.getString(
            R.string.total_debts,
            summaryToString(data.debtsSumTotal.toLong())
        )
        debtsAdapter.update(data.debtors)
        debtsAdapter.notifyDataSetChanged()
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
        Toast.makeText(context, "Error: $code $description", Toast.LENGTH_SHORT).show()
    }

    override fun showDeleteDialog() {
        Toast.makeText(context, "DeleteDialog", Toast.LENGTH_SHORT).show()
    }

    override fun showChatScreen(idDebtor: Int) {
        component().routingModule.navigationHolder.setNavigator(
            AppNavigator(
                requireActivity(),
                R.id.fragment_container
            )
        )
        component().routingModule.router.navigateTo(
            Screens.Chat("Александр", 12000, "url")
        )
//        component().routingModule.router.navigateTo(Screens.Chat(idDebtor))
    }
}
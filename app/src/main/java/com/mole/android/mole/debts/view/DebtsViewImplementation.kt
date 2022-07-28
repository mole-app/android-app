package com.mole.android.mole.debts.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.R
import com.mole.android.mole.component
import com.mole.android.mole.databinding.FragmentDebtsMainBinding
import com.mole.android.mole.debts.data.DebtsData
import com.mole.android.mole.navigation.Screens

class DebtsViewImplementation :
    MoleBaseFragment<FragmentDebtsMainBinding>(FragmentDebtsMainBinding::inflate), DebtsView {

    private val presenter = component().debtsModule.debtsPresenter
    override fun getNavigator() = AppNavigator(requireActivity(), R.id.nav_host_fragment)

    private val adapter = DebtsViewAdapter(object : OnItemChatClickListener {
        override fun onLongClick(view: View, chatData: DebtsData.ChatDebtsData) {
            presenter.onLongChatClick()
        }

        override fun onShotClick(chatData: DebtsData.ChatDebtsData) {
            component().routingModule.navigationHolder.setNavigator(
                AppNavigator(
                    requireActivity(),
                    R.id.fragment_container
                )
            )
            component().routingModule.router.navigateTo(
                Screens.Chat(
                    0
                )
            )
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.DebtsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.DebtsRecyclerView.adapter = adapter
        adapter.setData(presenter.getData())
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun setData(data: List<DebtsData>) {
        adapter.setData(data)
        adapter.notifyDataSetChanged()
    }
}
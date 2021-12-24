package com.mole.android.mole.debts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.R
import com.mole.android.mole.component
import com.mole.android.mole.databinding.FragmentDebtsMainBinding
import com.mole.android.mole.debts.data.DebtsData

class DebtsViewImplementation : MoleBaseFragment(), DebtsView {
    private var _binding : FragmentDebtsMainBinding? = null
    private val binding get() = _binding!!
    private val presenter = component().debtsModule.debtsPresenter
    override fun getNavigator() = AppNavigator(requireActivity(), R.id.nav_host_fragment)

    private val adapter = DebtsViewAdapter(object : OnItemChatClickListener{
        override fun onLongClick(view:View, chatData: DebtsData.ChatDebtsData) {
            presenter.onLongChatClick()
        }

        override fun onShotClick(chatData: DebtsData.ChatDebtsData) {
            presenter.onShortChatClick()
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding= FragmentDebtsMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.DebtsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.DebtsRecyclerView.adapter = adapter
        adapter.setData(presenter.getData())
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
        _binding = null
    }

    override fun setData(data: List<DebtsData>) {
        adapter.setData(data)
        adapter.notifyDataSetChanged()
    }
}
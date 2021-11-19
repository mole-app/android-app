package com.mole.android.mole.debts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.databinding.FragmentDebtsMainBinding
import com.mole.android.mole.debts.data.DebtsData
import com.mole.android.mole.debts.data.testData

class DebtsViewImplementation : MoleBaseFragment(), DebtsView {
    private var _binding : FragmentDebtsMainBinding? = null
    private val binding get() = _binding!!

    private val adapter = DebtsMainAdapter(object : OnItemChatClickListener{
        override fun onLongClick(view:View, chatData: DebtsData.ChatDebtsData) {
            Toast.makeText(context, "LongClick", Toast.LENGTH_LONG).show()
            onLongChatClick()
        }

        override fun onShotClick(chatData: DebtsData.ChatDebtsData) {
            Toast.makeText(context, "ShortClick", Toast.LENGTH_LONG).show()
            onShortChatClick()
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
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.DebtsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.DebtsRecyclerView.adapter = adapter
        adapter.setData(testData)
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onLongChatClick() {
        TODO("Not yet implemented")
    }

    override fun onShortChatClick() {
        TODO("Not yet implemented")
    }

    override fun onSearchClick() {
        TODO("Not yet implemented")
    }
}
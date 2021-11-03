package com.mole.android.mole.deps.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.databinding.FragmentDebtsMainBinding
import com.mole.android.mole.deps.data.ChatData
import com.mole.android.mole.deps.data.testData

class DebtsMainFragment : MoleBaseFragment() {
    private var _binding : FragmentDebtsMainBinding? = null
    private val binding get() = _binding!!

    private var adapter = DebtsMainAdapter(object : OnItemChatClickListener{
        override fun onLongClick(chatData: ChatData) {
            Toast.makeText(context, "LongClick", Toast.LENGTH_LONG).show()
        }

        override fun onShotClick(chatData: ChatData) {
            Toast.makeText(context, "ShortClick", Toast.LENGTH_LONG).show()
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
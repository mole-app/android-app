package com.mole.android.mole

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class MoleBaseFragment<T : ViewBinding>
    (private val inflation: (LayoutInflater, ViewGroup?, Boolean) -> T) : Fragment() {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    open fun getToolbar(): Toolbar? {
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = getToolbar()
        if (toolbar != null) {
            val appCompatActivity = requireActivity()
            if (appCompatActivity is AppCompatActivity) {
                appCompatActivity.setSupportActionBar(toolbar)
                appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = inflation(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
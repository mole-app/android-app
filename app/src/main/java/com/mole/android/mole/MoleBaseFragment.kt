package com.mole.android.mole

import android.os.Bundle
import android.view.*
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mole.android.mole.ui.actionbar.MoleActionBar

abstract class MoleBaseFragment<T : ViewBinding>
    (private val inflation: (LayoutInflater, ViewGroup?, Boolean) -> T) : Fragment() {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    private val navigatorHolder = component().routingModule.navigationHolder
    private val mainActivity: MainActivity get() = activity as MainActivity

    open fun getNavigator(): Navigator = AppNavigator(requireActivity(), R.id.fragment_container)
    open fun getToolbar(): MoleActionBar? = null

    @MenuRes
    open fun getMenuId(): Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = getToolbar()
        if (toolbar != null) {
            val appCompatActivity = requireActivity()
            if (appCompatActivity is AppCompatActivity) {
                appCompatActivity.setSupportActionBar(toolbar)
                appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
                if (getMenuId() != 0) {
                    setHasOptionsMenu(true)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(getMenuId(), menu)
        getToolbar()?.bindMenu()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mainActivity.stackFragments.add(this)
    }

    override fun onDestroy() {
//        mainActivity.stackFragments.remove(this)
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(getNavigator())
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    open fun onBackPress(): Boolean {
        return false
    }
}
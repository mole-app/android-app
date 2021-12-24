package com.mole.android.mole

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator

abstract class MoleBaseFragment : Fragment() {

    private val navigatorHolder = component().routingModule.navigationHolder
    private val mainActivity: MainActivity get() = activity as MainActivity

    open fun getNavigator(): Navigator = AppNavigator(requireActivity(), R.id.fragment_container)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity.stackFragments.add(this)
    }

    override fun onDestroy() {
        mainActivity.stackFragments.remove(this)
        super.onDestroy()
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
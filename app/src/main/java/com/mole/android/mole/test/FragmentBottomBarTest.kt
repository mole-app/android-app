package com.mole.android.mole.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.R
import com.mole.android.mole.component
import com.mole.android.mole.navigation.Screens
import com.mole.android.mole.ui.appbar.MoleBottomNavigationBar

class FragmentBottomBarTest : MoleBaseFragment() {

    private var currentFragmentTag = TAG_1
    private val router = component().routingModule.router
    override fun getNavigator(): Navigator {
        return AppNavigator(requireActivity(), R.id.nav_host_fragment)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(FRAGMENT_TAG_KEY, currentFragmentTag)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_with_botnav, container, false)

        currentFragmentTag = savedInstanceState?.getString(FRAGMENT_TAG_KEY) ?: TAG_1

        when (currentFragmentTag) {
            TAG_1 -> {
                replaceOnTestScreen(currentFragmentTag, TestScreenFragment())
            }
            TAG_2 -> {
                replaceOnTestScreen(currentFragmentTag, FragmentTest())
            }
        }

        val navigationAppBar: MoleBottomNavigationBar =
            view.findViewById(R.id.mole_bottom_navigation_bar)

        navigationAppBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_debts -> {
                    replaceOnTestScreen(TAG_1, TestScreenFragment())
                }
                R.id.navigation_profile -> {
                    replaceOnTestScreen(TAG_2, FragmentTest())
                }
            }
            true
        }
        return view
    }

    private fun replaceOnTestScreen(tag: String, fragment: Fragment) {
        val fragment1: Fragment? =
            requireActivity().supportFragmentManager.findFragmentByTag(tag)

        if (fragment1 == null) {
            currentFragmentTag = tag
            val fragmentTransaction: FragmentTransaction =
                requireActivity().supportFragmentManager
                    .beginTransaction()
            fragmentTransaction.replace(
                R.id.nav_host_fragment, fragment, tag
            )
            fragmentTransaction.commit()
        }
    }

    companion object {
        const val TAG_1 = "FRAGMENT_1"
        const val TAG_2 = "FRAGMENT_2"
        private const val FRAGMENT_TAG_KEY = "FRAGMENT_KEY"
    }

}
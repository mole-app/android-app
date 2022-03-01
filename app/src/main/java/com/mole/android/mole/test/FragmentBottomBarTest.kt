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

        val navigationAppBar: MoleBottomNavigationBar =
            view.findViewById(R.id.mole_bottom_navigation_bar)

        navigationAppBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_debts -> {
                    router.newRootScreen(Screens.Debts())
                }
                R.id.navigation_profile -> {
                    router.newChain(Screens.DevPanel())
                }
            }
            true
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        when (currentFragmentTag) {
            TAG_1 -> {
                router.newRootScreen(Screens.Debts())
            }
            TAG_2 -> {
                router.newChain(Screens.DevPanel())
            }
        }
    }

    companion object {
        private const val TAG_1 = "FRAGMENT_1"
        private const val TAG_2 = "FRAGMENT_2"
        private const val FRAGMENT_TAG_KEY = "FRAGMENT_KEY"
    }

}
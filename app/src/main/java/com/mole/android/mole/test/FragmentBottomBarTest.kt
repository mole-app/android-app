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
import com.mole.android.mole.databinding.FragmentWithBotnavBinding
import com.mole.android.mole.navigation.Screens
import com.mole.android.mole.ui.appbar.MoleBottomNavigationBar

class FragmentBottomBarTest :
    MoleBaseFragment<FragmentWithBotnavBinding>(FragmentWithBotnavBinding::inflate) {

    private var currentFragmentTag = TAG_1
    private val router = component().routingModule.router
    override fun getNavigator(): Navigator {
        return AppNavigator(requireActivity(), R.id.nav_host_fragment)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(FRAGMENT_TAG_KEY, currentFragmentTag)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentFragmentTag = savedInstanceState?.getString(FRAGMENT_TAG_KEY) ?: TAG_1

        binding.moleBottomNavigationBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_debts -> {
                    router.newRootScreen(Screens.Debts())
                }
                R.id.navigation_profile -> {
                    router.navigateTo(Screens.DevPanel())
                }
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        when (currentFragmentTag) {
            TAG_1 -> {
                router.newRootScreen(Screens.Debts())
            }
            TAG_2 -> {
                router.navigateTo(Screens.DevPanel())
            }
        }
    }

    companion object {
        private const val TAG_1 = "FRAGMENT_1"
        private const val TAG_2 = "FRAGMENT_2"
        private const val FRAGMENT_TAG_KEY = "FRAGMENT_KEY"
    }

}
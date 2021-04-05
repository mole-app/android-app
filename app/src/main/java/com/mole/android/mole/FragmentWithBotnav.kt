package com.mole.android.mole

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.mole.android.mole.ui.mole_bottom_navigation.MoleBottomNavigation
import com.mole.android.mole.ui.mole_bottom_navigation.bottom_navigation_bar.BottomNavigationView

class FragmentWithBotnav : Fragment() {

    companion object {
        const val TAG_1 = "FRAGMENT_1"
        const val TAG_2 = "FRAGMENT_2"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_with_botnav, container, false)

        val fragment: TestScreenFragment? =
            requireActivity().supportFragmentManager.findFragmentByTag(TAG_1) as TestScreenFragment?

        if (fragment == null) {
            val fragmentTransaction: FragmentTransaction = requireActivity().supportFragmentManager
                .beginTransaction()
            fragmentTransaction.add(
                R.id.nav_host_fragment, TestScreenFragment(), TAG_1
            )
            fragmentTransaction.commit()
        }

        val navigationBar: MoleBottomNavigation = view.findViewById(R.id.navigationCoordinatorLayout)

        navigationBar.setOnNavigationItemSelectedListener { item ->
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
            val fragmentTransaction: FragmentTransaction =
                requireActivity().supportFragmentManager
                    .beginTransaction()
            fragmentTransaction.replace(
                R.id.nav_host_fragment, fragment, tag
            )
            fragmentTransaction.commit()
        } else {
            Toast.makeText(
                requireActivity(), "tag: '$tag' already press",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}
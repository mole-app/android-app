package com.mole.android.mole

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mole.android.mole.ui.navigation_bar.BottomNavigationView

class FragmentWithBotnav : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_with_botnav, container, false)
        val nav: BottomNavigationView = view.findViewById(R.id.bottomNavigation)
        nav.menu.getItem(1).isEnabled = false
        val navController = findNavController(view.findViewById(R.id.nav_host_fragment))
        nav.setupWithNavController(navController)

        return view
    }
}
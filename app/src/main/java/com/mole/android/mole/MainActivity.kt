package com.mole.android.mole

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class MainActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return FragmentWithBotnav()
//        return TestScreenFragment()
    }
}
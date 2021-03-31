package com.mole.android.mole

import androidx.fragment.app.Fragment

class MainActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
//        return FragmentWithBotnav()
        return TestScreenFragment()
    }
}
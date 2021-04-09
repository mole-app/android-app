package com.mole.android.mole

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mole.android.mole.testStuff.FragmentBottomBar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment == null) {
            val newFragment = createFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, newFragment)
                .commit()
        }
    }

    private fun createFragment(): Fragment {
        return FragmentBottomBar()
    }
}

package com.mole.android.mole

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mole.android.mole.debts.view.DebtsViewImplementation


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigator = AppNavigator(this, R.id.fragment_container)
        component().routingModule.navigationHolder.setNavigator(navigator)

        val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment == null) {
            val newFragment = createFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, newFragment)
                .commit()
        }
    }

    private fun createFragment(): Fragment {
//        return FragmentBottomBar()
//        return AuthLoginViewImplementation()
        return DebtsViewImplementation()
//        return AuthBeginViewImplementation()
    }
}

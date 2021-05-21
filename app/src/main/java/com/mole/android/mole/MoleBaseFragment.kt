package com.mole.android.mole

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

abstract class MoleBaseFragment : Fragment() {

    open fun getToolbar(): Toolbar? {
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = getToolbar()
        if (toolbar != null) {
            (requireActivity() as AppCompatActivity).setSupportActionBar(getToolbar())
            (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(
                false
            )
        }
    }
}
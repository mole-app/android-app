package com.mole.android.mole.TestStuff

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mole.android.mole.R


class TestScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.test_screen, container, false)
        val circleImageButton: AppCompatImageButton = view.findViewById(R.id.test_screen_circle_button_disable)
        circleImageButton.isEnabled = false

        val toolbar: Toolbar = view.findViewById(R.id.toolbarTest)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
//        toolbar.inflateMenu(R.menu.bottom_nav_menu)

        return view
    }
}

package com.mole.android.mole

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

class TestScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.test_screen, container, false)
        val circleImageButton: AppCompatImageButton = view.findViewById(R.id.test_screen_circle_button_disable)
        circleImageButton.isEnabled = false
        return view
    }
}

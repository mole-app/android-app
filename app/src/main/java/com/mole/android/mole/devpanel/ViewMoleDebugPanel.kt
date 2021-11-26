package com.mole.android.mole.devpanel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.R

class ViewMoleDebugPanel : MoleBaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.frg_debug_panel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonRemoveToken: AppCompatButton = view.findViewById(R.id.debug_panel_remove_token)
        buttonRemoveToken.setOnClickListener {
            Toast.makeText(context, "Remove", Toast.LENGTH_SHORT).show()

        }

        val buttonBack: AppCompatButton = view.findViewById(R.id.debug_panel_button_back)
        buttonBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().hide(this).commit()
        }
    }
}
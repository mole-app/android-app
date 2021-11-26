package com.mole.android.mole.devpanel.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.R
import com.mole.android.mole.component
import com.mole.android.mole.devpanel.presentation.MoleDebugPanelPresenter
import com.mole.android.mole.navigation.Screens

class MoleDebugPanelViewImpl : MoleBaseFragment(), MoleDebugPanelView {

    private val presenter = MoleDebugPanelPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.frg_debug_panel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonRemoveToken: AppCompatButton = view.findViewById(R.id.debug_panel_remove_token)
        buttonRemoveToken.setOnClickListener {
            presenter.onButtonRemoveToken()
        }

        val buttonBack: AppCompatButton = view.findViewById(R.id.debug_panel_button_back)
        buttonBack.setOnClickListener {
            presenter.onButtonBack()
        }

        presenter.attachView(this)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun hide() {
        component().routingModule.router.exit()
    }
}
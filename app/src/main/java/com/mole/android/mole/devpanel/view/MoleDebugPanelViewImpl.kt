package com.mole.android.mole.devpanel.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.mole.android.mole.*

class MoleDebugPanelViewImpl : MoleBaseFragment(), MoleDebugPanelView {

    private val presenter = component().devPanelModule.devPanelPresenter

    private lateinit var buttonCorruptedAccessToken: AppCompatButton
    private lateinit var buttonCorruptedRefreshToken: AppCompatButton
    private lateinit var buttonBack: AppCompatButton
    private lateinit var buttonRemoveAccount: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.frg_debug_panel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonCorruptedAccessToken =
            view.findViewById(R.id.debug_panel_corrupted_token_access)
        buttonCorruptedAccessToken.setOnClickListener {
            presenter.onButtonCorruptedAccessToken()
        }
        buttonCorruptedAccessToken.setBorder(
            Shape.RECTANGLE,
            80f.dp()
        )

        buttonCorruptedRefreshToken =
            view.findViewById(R.id.debug_panel_corrupted_token_refresh)
        buttonCorruptedRefreshToken.setOnClickListener {
            presenter.onButtonCorruptedRefreshToken()
        }
        buttonCorruptedRefreshToken.setBorder(
            Shape.RECTANGLE,
            80f.dp()
        )

        buttonBack = view.findViewById(R.id.debug_panel_button_back)
        buttonBack.setOnClickListener {
            presenter.onButtonBack()
        }
        buttonBack.setBorder(
            Shape.RECTANGLE,
            80f.dp()
        )

        buttonRemoveAccount =
            view.findViewById(R.id.debug_panel_remove_account)
        buttonRemoveAccount.setOnClickListener {
            presenter.onButtonRemoveAccount()
        }
        buttonRemoveAccount.setBorder(
            Shape.RECTANGLE,
            80f.dp()
        )

        presenter.attachView(this)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun hide() {
        component().routingModule.router.exit()
    }

    override fun corruptedAccessButtonEnable(enable: Boolean) {
        buttonCorruptedAccessToken.isEnabled = enable
    }

    override fun corruptedRefreshButtonEnable(enable: Boolean) {
        buttonCorruptedRefreshToken.isEnabled = enable
    }

    override fun removeButtonEnable(enable: Boolean) {
        buttonRemoveAccount.isEnabled = enable
    }

    override fun isHasAccount(): Boolean {
        return component().accountManagerModule.isHasAccount()
    }

    override fun removeAccount() {
        val accountModule = component().accountManagerModule
        accountModule.removeAccount {

        }
    }
}
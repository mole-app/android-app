package com.mole.android.mole.devpanel.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.mole.android.mole.*
import com.mole.android.mole.devpanel.presentation.MoleDebugPanelPresenter

class MoleDebugPanelViewImpl : MoleBaseFragment(), MoleDebugPanelView {

    private val presenter = MoleDebugPanelPresenter()

    private lateinit var buttonCorruptedToken: AppCompatButton
    private lateinit var buttonBack: AppCompatButton
    private lateinit var buttonRemoveAccount: AppCompatButton

    companion object {
        const val CORRUPTED_PART = "jgfsf78gfie4bfgqt8436ghf9q34fqo8fon"
    }

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

        buttonCorruptedToken =
            view.findViewById(R.id.debug_panel_corrupted_token)
        buttonCorruptedToken.setOnClickListener {
            presenter.onButtonCorruptedToken()
        }
        buttonCorruptedToken.setBorder(
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

    override fun corruptedToken() {
        val accountManager = component().accountManager
        val accounts = accountManager.getAccountsByType("com.mole.android.mole")
        val account = accounts[0]
        val accessToken = accountManager.peekAuthToken(account, "accessAuthToken")
        val corruptedAccessToken =
            accessToken.removeRange(accessToken.length / 2, accessToken.length) + CORRUPTED_PART
        accountManager.setAuthToken(account, "accessAuthToken", corruptedAccessToken)
    }

    override fun corruptedButtonEnable(enable: Boolean) {
        buttonCorruptedToken.isEnabled = enable
    }

    override fun removeButtonEnable(enable: Boolean) {
        buttonRemoveAccount.isEnabled = enable
    }

    override fun isHasAccount(): Boolean {
        val accountManager = component().accountManager
        val accounts = accountManager.getAccountsByType("com.mole.android.mole")
        return !accounts.isEmpty()
    }

    override fun removeAccount() {
        val accountManager = component().accountManager
        val accounts = accountManager.getAccountsByType("com.mole.android.mole")
        val account = accounts[0]
        accountManager.removeAccount(
            account, requireActivity(),
            { Toast.makeText(requireContext(), "Account removed", Toast.LENGTH_SHORT).show() },
            null
        )
        val accessToken = accountManager.peekAuthToken(account, "accessAuthToken")
        val corruptedAccessToken =
            accessToken.removeRange(accessToken.length / 2, accessToken.length) + CORRUPTED_PART
        accountManager.setAuthToken(account, "accessAuthToken", corruptedAccessToken)
    }
}
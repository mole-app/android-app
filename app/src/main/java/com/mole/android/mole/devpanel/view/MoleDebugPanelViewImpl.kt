package com.mole.android.mole.devpanel.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.mole.android.mole.*
import com.mole.android.mole.devpanel.presentation.MoleDebugPanelPresenter
import com.mole.android.mole.di.AccountManagerModule.Companion.ACCESS_TOKEN
import com.mole.android.mole.di.AccountManagerModule.Companion.REFRESH_TOKEN

class MoleDebugPanelViewImpl : MoleBaseFragment(), MoleDebugPanelView {

    private val presenter = MoleDebugPanelPresenter()

    private lateinit var buttonCorruptedAccessToken: AppCompatButton
    private lateinit var buttonCorruptedRefreshToken: AppCompatButton
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

    override fun corruptedAccessToken() {
        val accountManager = component().accountManagerModule.accountManager
        val accounts = accountManager.getAccountsByType(BuildConfig.APPLICATION_ID)
        val account = accounts[0]
        val accessToken = accountManager.peekAuthToken(account, ACCESS_TOKEN)
        val corruptedAccessToken =
            accessToken.removeRange(accessToken.length / 2, accessToken.length) + CORRUPTED_PART
        accountManager.setAuthToken(account, ACCESS_TOKEN, corruptedAccessToken)
    }

    override fun corruptedRefreshToken() {
        val accountManager = component().accountManagerModule.accountManager
        val accounts = accountManager.getAccountsByType(BuildConfig.APPLICATION_ID)
        val account = accounts[0]
        val refreshToken = accountManager.peekAuthToken(account, REFRESH_TOKEN)
        val corruptedRefreshToken =
            refreshToken.removeRange(refreshToken.length / 2, refreshToken.length) + CORRUPTED_PART
        accountManager.setAuthToken(account, REFRESH_TOKEN, corruptedRefreshToken)
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
        val accountManager = component().accountManagerModule.accountManager
        val accounts = accountManager.getAccountsByType(BuildConfig.APPLICATION_ID)
        return accounts.isNotEmpty()
    }

    override fun removeAccount() {
        val accountManager = component().accountManagerModule.accountManager
        val accounts = accountManager.getAccountsByType(BuildConfig.APPLICATION_ID)
        val account = accounts[0]
        accountManager.removeAccount(
            account, requireActivity(),
            { Toast.makeText(requireContext(), "Account removed", Toast.LENGTH_SHORT).show() },
            null
        )
    }
}
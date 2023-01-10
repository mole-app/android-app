package com.mole.android.mole.devpanel.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mole.android.mole.*
import com.mole.android.mole.databinding.FrgDebugPanelBinding
import com.mole.android.mole.di.repository.PreferenceRepository
import com.mole.android.mole.di.repository.Repository
import com.mole.android.mole.di.repository.RepositoryKeys.baseHost
import com.mole.android.mole.di.repository.RepositoryKeys.baseHostDefault
import com.mole.android.mole.di.repository.RepositoryKeys.customPort
import com.mole.android.mole.di.repository.RepositoryKeys.customPortDefault
import com.mole.android.mole.di.repository.RepositoryKeys.customPortEnable
import com.mole.android.mole.di.repository.RepositoryKeys.customPortEnableDefault
import com.mole.android.mole.di.repository.RepositoryKeys.enableUnsecureDefault
import com.mole.android.mole.di.repository.RepositoryKeys.enableUnsecureKey
import com.mole.android.mole.di.repository.RepositoryKeys.leakCanaryEnableKey

class MoleDebugPanelViewImpl :
    MoleBaseFragment<FrgDebugPanelBinding>(FrgDebugPanelBinding::inflate), MoleDebugPanelView {

    private val repository: Repository by lazy { PreferenceRepository(requireActivity()) }
    private val presenter = component().devPanelModule.devPanelPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.leakCanarySwitcher.isChecked = repository.getBoolean(leakCanaryEnableKey, false)
        binding.leakCanarySwitcher.setOnCheckedChangeListener { _, check ->
            repository.setBoolean(leakCanaryEnableKey, check)
            LeakAnalyser().enableIfNeeded(requireActivity())
        }

        binding.networkSwitcher.isChecked =
            repository.getBoolean(enableUnsecureKey, enableUnsecureDefault)
        binding.networkSwitcher.setOnCheckedChangeListener { _, check ->
            repository.setBoolean(enableUnsecureKey, check)
        }

        binding.debugPanelCorruptedTokenAccess.setOnClickListener {
            presenter.onButtonCorruptedAccessToken()
        }
        binding.debugPanelCorruptedTokenAccess.setupBorder(
            Shape.RECTANGLE,
            80f.dp
        )

        binding.debugPanelCorruptedTokenRefresh.setOnClickListener {
            presenter.onButtonCorruptedRefreshToken()
        }
        binding.debugPanelCorruptedTokenRefresh.setupBorder(
            Shape.RECTANGLE,
            80f.dp
        )

        binding.debugPanelButtonBack.setOnClickListener {
            presenter.onButtonBack()
            notifyAboutReload()
        }
        binding.debugPanelButtonBack.setupBorder(
            Shape.RECTANGLE,
            80f.dp
        )

        binding.debugPanelRemoveAccount.setOnClickListener {
            presenter.onButtonRemoveAccount()
        }
        binding.debugPanelRemoveAccount.setupBorder(
            Shape.RECTANGLE,
            80f.dp
        )

        binding.debugPanelRemoveRemoteAccount.setOnClickListener {
            presenter.onButtonRemoveRemoteAccount()
        }
        binding.debugPanelRemoveRemoteAccount.setupBorder(
            Shape.RECTANGLE,
            80f.dp
        )

        binding.hostEditText.setText(repository.getString(baseHost, baseHostDefault))
        binding.hostEditText.onTextChanged { host ->
            repository.setString(baseHost, host.toString())
        }

        val customPortEnableValue = repository.getBoolean(customPortEnable, customPortEnableDefault)
        binding.customPort.isChecked = customPortEnableValue
        binding.customPort.setOnCheckedChangeListener { _, check ->
            binding.customPortEditText.isEnabled = check
            repository.setBoolean(customPortEnable, check)
        }

        binding.customPortEditText.isEnabled = customPortEnableValue
        binding.customPortEditText.setText(
            repository.getInt(customPort, customPortDefault).toString()
        )
        binding.customPortEditText.onTextChanged { port ->
            repository.setInt(customPort, port.toString().toInt())
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

    override fun corruptedAccessButtonEnable(enable: Boolean) {
        binding.debugPanelCorruptedTokenAccess.isEnabled = enable
    }

    override fun corruptedRefreshButtonEnable(enable: Boolean) {
        binding.debugPanelCorruptedTokenRefresh.isEnabled = enable
    }

    override fun removeButtonEnable(enable: Boolean) {
        binding.debugPanelRemoveAccount.isEnabled = enable
    }

    override fun removeRemoteButtonEnable(enable: Boolean) {
        binding.debugPanelRemoveRemoteAccount.isEnabled = enable
    }

    override fun onBackPress(): Boolean {
        presenter.onButtonBack()
        notifyAboutReload()
        return true
    }

    private fun notifyAboutReload() {
        Toast.makeText(
            requireContext(),
            "Перезагрузите приложение чтобы изменения вступили в силу",
            Toast.LENGTH_LONG
        ).show()
    }
}
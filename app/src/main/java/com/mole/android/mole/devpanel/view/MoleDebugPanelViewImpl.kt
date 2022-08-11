package com.mole.android.mole.devpanel.view

import android.os.Bundle
import android.view.View
import com.mole.android.mole.*
import com.mole.android.mole.databinding.FrgDebugPanelBinding
import leakcanary.LeakCanary

class MoleDebugPanelViewImpl :
    MoleBaseFragment<FrgDebugPanelBinding>(FrgDebugPanelBinding::inflate), MoleDebugPanelView {

    private val presenter = component().devPanelModule.devPanelPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.leakCanarySwitcher.setOnCheckedChangeListener { _, check ->
            LeakCanary.config = LeakCanary.config.copy(dumpHeap = check)
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
}
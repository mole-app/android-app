package com.mole.android.mole.devpanel.view

import android.os.Bundle
import android.view.View
import com.mole.android.mole.*
import com.mole.android.mole.databinding.FrgDebugPanelBinding

class MoleDebugPanelViewImpl :
    MoleBaseFragment<FrgDebugPanelBinding>(FrgDebugPanelBinding::inflate), MoleDebugPanelView {

    private val presenter = component().devPanelModule.devPanelPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.debugPanelCorruptedTokenAccess.setOnClickListener {
            presenter.onButtonCorruptedAccessToken()
        }
        binding.debugPanelCorruptedTokenAccess.setBorder(
            Shape.RECTANGLE,
            80f.dp()
        )

        binding.debugPanelCorruptedTokenRefresh.setOnClickListener {
            presenter.onButtonCorruptedRefreshToken()
        }
        binding.debugPanelCorruptedTokenRefresh.setBorder(
            Shape.RECTANGLE,
            80f.dp()
        )

        binding.debugPanelButtonBack.setOnClickListener {
            presenter.onButtonBack()
        }
        binding.debugPanelButtonBack.setBorder(
            Shape.RECTANGLE,
            80f.dp()
        )

        binding.debugPanelRemoveAccount.setOnClickListener {
            presenter.onButtonRemoveAccount()
        }
        binding.debugPanelRemoveAccount.setBorder(
            Shape.RECTANGLE,
            80f.dp()
        )

        binding.debugPanelRemoveRemoteAccount.setOnClickListener {
            presenter.onButtonRemoveRemoteAccount()
        }
        binding.debugPanelRemoveRemoteAccount.setBorder(
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
package com.mole.android.mole.devpanel.view

import com.mole.android.mole.MoleBaseView

interface MoleDebugPanelView : MoleBaseView {
    fun hide()

    fun corruptedAccessButtonEnable(enable: Boolean)

    fun corruptedRefreshButtonEnable(enable: Boolean)

    fun removeButtonEnable(enable: Boolean)

    fun removeRemoteButtonEnable(enable: Boolean)

}
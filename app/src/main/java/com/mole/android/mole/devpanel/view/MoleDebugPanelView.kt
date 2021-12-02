package com.mole.android.mole.devpanel.view

interface MoleDebugPanelView {
    fun hide()

    fun removeAccount()

    fun corruptedAccessButtonEnable(enable: Boolean)

    fun corruptedRefreshButtonEnable(enable: Boolean)

    fun removeButtonEnable(enable: Boolean)

    fun isHasAccount(): Boolean

}
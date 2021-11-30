package com.mole.android.mole.devpanel.view

interface MoleDebugPanelView {
    fun hide()

    fun corruptedToken()

    fun removeAccount()

    fun corruptedButtonEnable(enable: Boolean)

    fun removeButtonEnable(enable: Boolean)

    fun isHasAccount(): Boolean
}
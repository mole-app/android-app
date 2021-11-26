package com.mole.android.mole.devpanel.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.devpanel.view.MoleDebugPanelView

class MoleDebugPanelPresenter(): MoleBasePresenter<MoleDebugPanelView>() {
    fun onButtonBack(){
        view?.hide()
    }

    fun onButtonRemoveToken() {

    }

}
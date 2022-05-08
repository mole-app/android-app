package com.mole.android.mole.create.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.create.view.name.ChooseNameView
import com.mole.android.mole.create.view.name.usersTestData

class ChooseNamePresenter : MoleBasePresenter<ChooseNameView>() {

    private var data = usersTestData.map {
        ChooseNameView.UserPreviewUi(it, "")
    }
    private var filtered = data

    override fun attachView(view: ChooseNameView) {
        super.attachView(view)
        filtered = data.toMutableList()
        view.show(data)
    }

    fun onInputChange(text: String) {
        filtered = data.filter {
                    it.userPreview.name.contains(text, true) ||
                            it.userPreview.login.contains(text, true)
                }
                    .map { ChooseNameView.UserPreviewUi(it.userPreview, text) }
                    .toMutableList()
        view?.show(filtered)
    }

}
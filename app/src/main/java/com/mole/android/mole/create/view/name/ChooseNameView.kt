package com.mole.android.mole.create.view.name

import com.mole.android.mole.MoleBaseView
import com.mole.android.mole.create.model.UserPreview

interface ChooseNameView : MoleBaseView {
    data class UserPreviewUi(val userPreview: UserPreview, val highlightFilter: String)
    fun show(data: List<UserPreviewUi>)
    fun showProgress()
    fun showError()
    fun showKeyboard()
    fun showEmptyState()
}
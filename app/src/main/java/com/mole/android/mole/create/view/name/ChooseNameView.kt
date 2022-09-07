package com.mole.android.mole.create.view.name

import com.mole.android.mole.MoleBaseView
import com.mole.android.mole.create.model.UserPreview

interface ChooseNameView : MoleBaseView {
    data class UserPreviewUi(val userPreview: UserPreview, val highlightFilter: String) {
        override fun equals(other: Any?): Boolean {
            if (other == null) return false
            if (other is UserPreviewUi) {
                return this.userPreview.id == other.userPreview.id && this.highlightFilter == other.highlightFilter && this.userPreview.login == other.userPreview.login && this.userPreview.name == other.userPreview.name
            }
            return false
        }
    }
    fun show(data: List<UserPreviewUi>)
    fun showProgress()
    fun showError()
    fun showKeyboard()
    fun showEmptyState()
}
package com.mole.android.mole.create.view.tag

import com.mole.android.mole.MoleBaseView
import com.mole.android.mole.create.model.TagPreview

interface ChooseTagView : MoleBaseView {
    data class TagPreviewUi(val isNew: Boolean, val preview: TagPreview)
    fun show(data: List<TagPreviewUi>)
    fun showProgress()
    fun showError()
    fun showKeyboard()
}
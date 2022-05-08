package com.mole.android.mole.create.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.create.model.TagPreview
import com.mole.android.mole.create.view.tag.ChooseTagView
import com.mole.android.mole.create.view.tag.tagsTestData

class ChooseTagPresenter : MoleBasePresenter<ChooseTagView>() {

    private val data: List<ChooseTagView.TagPreviewUi> = tagsTestData.map {
        it.toUi()
    }

    private var filtered = data.toMutableList()

    override fun attachView(view: ChooseTagView) {
        super.attachView(view)
        view.show(filtered)
    }

    fun onInputChange(text: String) {
        filtered = data.filter {
            it.preview.name.contains(text, true)
        }.toMutableList()

        val item = data.find { it.preview.name == text }
        if (item == null) {
            filtered.add(0, TagPreview(text, 0).toUi(true))
        }
        view?.show(filtered)
    }

    private fun TagPreview.toUi(isNew: Boolean = false): ChooseTagView.TagPreviewUi {
        return ChooseTagView.TagPreviewUi(
            isNew = isNew,
            preview = this
        )
    }
}
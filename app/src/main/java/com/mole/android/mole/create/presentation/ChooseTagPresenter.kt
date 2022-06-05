package com.mole.android.mole.create.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.create.data.ProvideTagsModel
import com.mole.android.mole.create.data.SuccessTagsResult
import com.mole.android.mole.create.model.TagPreview
import com.mole.android.mole.create.view.tag.ChooseTagView
import kotlinx.coroutines.launch

class ChooseTagPresenter(private val model: ProvideTagsModel) : MoleBasePresenter<ChooseTagView>() {

    private var data: List<ChooseTagView.TagPreviewUi>? = null
    private var lastInput: String? = null
    private var focusRequested = false

    override fun attachView(view: ChooseTagView) {
        super.attachView(view)
        view.showProgress()
        loadData(showKeyboard = true)
    }

    fun onInputChange(text: String) {
        val models = data
        lastInput = text
        if (models != null) {
            view?.show(models.applyFilter(text))
        }
    }

    fun onRetryClicked() {
        view?.showProgress()
        loadData()
    }

    fun onFocusRequested() {
        focusRequested = true
        if (data != null) {
            view?.showKeyboard()
        }
    }

    private fun loadData(showKeyboard: Boolean = false) {
        withScope {
            launch {
                model.provideTags()
                    .withResult { if (focusRequested && showKeyboard) view?.showKeyboard() }
                    .withResult { result -> handleResult(result) }
                    .withError { view?.showError() }
            }
        }
    }

    override fun detachView() {
        super.detachView()
        focusRequested = false
        lastInput = null
    }

    private fun handleResult(result: SuccessTagsResult) {
        val uiModels = result.toUi()
        val input = lastInput
        data = uiModels
        view?.show(uiModels.applyFilter(input ?: ""))
    }

    private fun Iterable<ChooseTagView.TagPreviewUi>.applyFilter(filter: String): List<ChooseTagView.TagPreviewUi> {
        val filtered = filter {
            it.preview.name.contains(filter, true)
        }.toMutableList()

        val item = find { it.preview.name == filter }
        if (item == null) {
            filtered.add(0, TagPreview(filter, 0).toUi(true))
        }
        return filtered
    }

    private fun SuccessTagsResult.toUi(): List<ChooseTagView.TagPreviewUi> {
        return map { it.toUi() }
    }

    private fun TagPreview.toUi(isNew: Boolean = false): ChooseTagView.TagPreviewUi {
        return ChooseTagView.TagPreviewUi(
            isNew = isNew,
            preview = this
        )
    }
}
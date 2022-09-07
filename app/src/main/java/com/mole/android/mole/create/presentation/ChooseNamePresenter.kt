package com.mole.android.mole.create.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.create.data.FindUserModel
import com.mole.android.mole.create.data.SuccessPreviewsResult
import com.mole.android.mole.create.view.name.ChooseNameView
import com.mole.android.mole.create.model.UserPreview
import com.mole.android.mole.throttleLatestWithFirst
import com.mole.android.mole.web.service.ApiResult
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

class ChooseNamePresenter(
    private val findUserModel: FindUserModel
) : MoleBasePresenter<ChooseNameView>() {

    private var disposable: Deferred<ApiResult<SuccessPreviewsResult>>? = null
    private var lastData: List<ChooseNameView.UserPreviewUi>? = null
    private var isInErrorState = false
    private var lastFilter: String = ""

    private var throttledShow: ((List<ChooseNameView.UserPreviewUi>) -> Unit)? = null

    override fun attachView(view: ChooseNameView) {
        super.attachView(view)
        view.showProgress()
        loadData {
            view.showKeyboard()
        }
        lastFilter = ""
    }

    fun onInputChange(text: String) {
        lastData?.let { data ->
            val newData = data.applyFilter(text)
            if (newData.isNotEmpty()) {
                show(newData)
            }
        }
        loadData(text)
    }

    fun onRetryClicked() {
        view?.showProgress()
        loadData(lastFilter)
    }

    override fun detachView() {
        super.detachView()
        throttledShow = null
    }

    private fun show(list: List<ChooseNameView.UserPreviewUi>) {
        if (throttledShow != null) {
            throttledShow?.invoke(list)
        } else {
            throttledShow = letScope { scope ->
                    throttleLatestWithFirst(500L, coroutineScope = scope) { list: List<ChooseNameView.UserPreviewUi> ->
                        view?.show(list)
                    }
                }
            throttledShow?.invoke(list)
        }
    }

    private fun loadData(filter: String = "", onSuccess: () -> Unit = {}) {
        lastFilter = filter
        disposable?.cancel()
        withScope {
            disposable = async {
                findUserModel.profilePreviews(filter)
                    .withResult { result -> handleResult(filter, result) }
                    .withResult { onSuccess() }
                    .withError { view?.showError() }
                    .withError { isInErrorState = true }
            }
        }
    }

    private fun handleResult(filter: String, result: SuccessPreviewsResult) {
        if (result.isEmpty()) {
            view?.showEmptyState()
            return
        }
        val uiModels = result.mapToUi(filter)
        lastData = uiModels
        show(uiModels)
    }

    private fun Iterable<ChooseNameView.UserPreviewUi>.applyFilter(filter: String): List<ChooseNameView.UserPreviewUi> {
        if (filter.isBlank()) return map {
            uiPreview -> uiPreview.copy(highlightFilter = filter)
        }
        return filter { uiPreview ->
            uiPreview.userPreview.name.contains(filter, true) ||
                    uiPreview.userPreview.login.contains(filter, true)
        }.map { uiPreview -> uiPreview.copy(highlightFilter = filter) }
    }

    private fun Iterable<UserPreview>.mapToUi(filter: String)
        = map { ChooseNameView.UserPreviewUi(userPreview = it, highlightFilter = filter) }

}
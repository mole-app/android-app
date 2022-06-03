package com.mole.android.mole.create.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.create.data.FindUserModel
import com.mole.android.mole.create.data.SuccessPreviewsResult
import com.mole.android.mole.create.view.name.ChooseNameView
import com.mole.android.mole.create.model.UserPreview
import com.mole.android.mole.web.service.ApiResult
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

class ChooseNamePresenter(
    private val findUserModel: FindUserModel
) : MoleBasePresenter<ChooseNameView>() {

    private var disposable: Deferred<ApiResult<SuccessPreviewsResult>>? = null
    private var lastData: List<ChooseNameView.UserPreviewUi>? = null

    override fun attachView(view: ChooseNameView) {
        super.attachView(view)
        view.showProgress()
        loadData()
    }

    fun onInputChange(text: String) {
        lastData?.let { data -> view?.show(data.applyFilter(text)) }
        loadData(text)
    }

    private fun loadData(filter: String = "") {
        disposable?.cancel()
        withScope {
            disposable = async {
                findUserModel.profilePreviews(filter)
                    .withResult { result -> handleResult(filter, result) }
                    .withError { view?.showError() }
            }
        }
    }

    private fun handleResult(filter: String, result: SuccessPreviewsResult) {
        val uiModels = result.mapToUi(filter)
        lastData = uiModels
        view?.show(uiModels)
    }

    private fun Iterable<ChooseNameView.UserPreviewUi>.applyFilter(filter: String): List<ChooseNameView.UserPreviewUi> {
        return filter { uiPreview ->
            uiPreview.userPreview.name.contains(filter, true) ||
                    uiPreview.userPreview.login.contains(filter, true)
        }.map { uiPreview -> uiPreview.copy(highlightFilter = filter) }
    }

    private fun Iterable<UserPreview>.mapToUi(filter: String)
        = map { ChooseNameView.UserPreviewUi(userPreview = it, highlightFilter = filter) }

}
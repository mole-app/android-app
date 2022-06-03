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

    override fun attachView(view: ChooseNameView) {
        super.attachView(view)
        view.showProgress()
        loadData()
    }

    fun onInputChange(text: String) {
        loadData(text)
    }

    private fun loadData(filter: String = "") {
        disposable?.cancel()
        withScope {
            disposable = async {
                findUserModel.profilePreviews(filter)
                    .withResult { result -> view?.show(result.mapToUi(filter)) }
                    .withError { view?.showError() }
            }
        }
    }

    private fun Iterable<UserPreview>.mapToUi(filter: String)
        = map { ChooseNameView.UserPreviewUi(it, filter) }

}
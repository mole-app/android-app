package com.mole.android.mole.chat.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.chat.model.ChatModel
import com.mole.android.mole.chat.view.ChatView
import kotlinx.coroutines.launch

class ChatPresenter(
    private val model: ChatModel,
) : MoleBasePresenter<ChatView>() {
    private var isDataLoading = false

    override fun attachView(view: ChatView) {
        super.attachView(view)
        view.setToolbarLoading()
        view.showLoading()
        dataLoading(view, view.getUserId(), true)
    }

    fun onCallbackToChat() {
        if (!isDataLoading) {
            withView { view ->
                view.setToolbarLoading()
                view.showLoading()
                dataLoading(view, view.getUserId())
            }
        }
    }

    fun onChatPreScrolledToTop() {
        if (!isDataLoading) {
            withView { view ->
                dataLoading(view, view.getUserId(), false, view.getIdDebtMax())
            }
        }
    }

    fun onChatScrolledToTop() {
        if (isDataLoading) {
            withView { view ->
                view.showLoading()
            }
        }
    }

    private fun dataLoading(
        view: ChatView,
        userId: Int,
        isLoadUserInfo: Boolean = false,
        idDebtMax: Int? = null
    ) {
        isDataLoading = true
        withScope {
            launch {
                model.loadChatData(userId, isLoadUserInfo, idDebtMax).withResult { result ->
                    when (result) {
                        is ChatModel.SuccessChatResult.DataWithUserInfo -> {
                            view.hideLoading()
                            view.setToolbarData(result.userInfo)
                            view.setData(result.chatData)
                            isDataLoading = false
                        }
                        is ChatModel.SuccessChatResult.DataBatch -> {
                            view.setData(result.chatData)
                            view.hideLoading()
                            isDataLoading = false
                        }
                        is ChatModel.SuccessChatResult.DataIsOver -> {
                            view.hideLoading()
                            isDataLoading = false
                        }
                    }
                }
            }
        }
    }
}

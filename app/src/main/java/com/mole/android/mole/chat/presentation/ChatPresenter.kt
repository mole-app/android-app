package com.mole.android.mole.chat.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.chat.model.ChatModel
import com.mole.android.mole.chat.view.ChatView
import kotlinx.coroutines.launch

class ChatPresenter(
    private val model: ChatModel,
) : MoleBasePresenter<ChatView>() {
    private var isDataLoading = false
    private var leftDataCount: Int = 0
    private val itemCountBeforeRequest = 10

    override fun attachView(view: ChatView) {
        super.attachView(view)
        view.showLoading()
        dataLoading(view)

    }

    fun onChatPreScrolledToTop(){
        if (!isDataLoading) {
            withView { view ->
                dataLoading(view)
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

    private fun dataLoading(view: ChatView) {
        isDataLoading = true
        withScope {
            launch {
                model.loadNextData(leftDataCount).withResult { result ->
                    when (result) {
                        is ChatModel.SuccessChatResult.DataBatch -> {
                            leftDataCount = result.chatData.size
                            view.setData(result.chatData)
                            view.hideLoading()
                            isDataLoading = false
                        }
                        is ChatModel.SuccessChatResult.DataIsOver -> {
                            leftDataCount = 0
                            view.hideLoading()
                            isDataLoading = false
                        }
                    }
                }
            }
        }
    }

    fun onDeleteItem(id: Int) {
        withScope {
            launch {
                model.deleteItem(id)
            }
        }
    }
}
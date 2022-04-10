package com.mole.android.mole.chat.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.chat.model.ChatModel
import com.mole.android.mole.chat.view.ChatView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ChatPresenter(
    private val model: ChatModel,
    private val scope: CoroutineScope
) : MoleBasePresenter<ChatView>() {
    private var isDataLoading = false
    private var leftDataCount: Int = 0

    override fun attachView(view: ChatView) {
        super.attachView(view)
        withView { view ->
            view.showLoading()
            dataLoading(view)
        }
    }

    fun loadData(lastItemVisiblePosition: Int, itemCount: Int) {
        if (!isDataLoading) {
            if (lastItemVisiblePosition + 1 + 10 >= itemCount) {
                withView { view ->
                    dataLoading(view)
                }
            }
        } else {
            if (lastItemVisiblePosition + 1 == itemCount) {
                withView { view ->
                    view.showLoading()
                }
            }
        }
    }

    private fun dataLoading(view: ChatView) {
        isDataLoading = true
        scope.launch {
            model.loadNextData(leftDataCount).withResult { result ->
                when (result) {
                    is ChatModel.SuccessChatResult.SuccessLoadData -> {
                        leftDataCount = result.chatData.size
                        view.setData(result.chatData)
                        view.hideLoading()
                        isDataLoading = false
                    }
                    is ChatModel.SuccessChatResult.SuccessDataAlreadyLoaded -> {
                        leftDataCount = 0
                        view.hideLoading()
                        isDataLoading = false
                    }
                }
            }
        }
    }
}
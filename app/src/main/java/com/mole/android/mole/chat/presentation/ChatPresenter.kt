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
            model.loadNextData().withResult { result ->
                view.setData(result.chatData)
                view.hideLoading()
                isDataLoading = false
            }
        }
    }
}
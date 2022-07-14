package com.mole.android.mole.chat.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.chat.model.ChatModel
import com.mole.android.mole.chat.view.ChatView
import kotlinx.coroutines.launch

class ChatPresenter(
    private val model: ChatModel,
) : MoleBasePresenter<ChatView>() {
    private var isDataLoading = false
    private var debtLeft = -1

    override fun attachView(view: ChatView) {
        super.attachView(view)
        view.setToolbarLoading()
        view.showLoading()
        loadData(view, view.getUserId(), true)
    }

    fun onDebtsCreated() {
        if (!isDataLoading) {
            withView { view ->
                view.setToolbarLoading()
                view.showLoading()
                loadData(view, view.getUserId())
            }
        }
    }

    fun onChatPreScrolledToTop() {
        if (!isDataLoading) {
            withView { view ->
                loadData(view, view.getUserId(), false, view.getIdDebtMax())
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

    private fun loadData(
        view: ChatView,
        userId: Int,
        isLoadUserInfo: Boolean = false,
        idDebtMax: Int? = null
    ) {
        isDataLoading = true
        withScope {
            launch {
                if (debtLeft != 0) {
                    model.loadChatData(userId, idDebtMax).withResult { result ->
                        debtLeft = result.debtLeft
                        if (isLoadUserInfo) {
                            view.setToolbarData(result.debtor)
                            view.setData(result.debts)
                        } else {
                            view.setData(result.debts)
                        }
                        view.hideLoading()
                        isDataLoading = false
                    }
                } else {
                    view.hideLoading()
                    isDataLoading = false
                }
            }
        }
    }
}

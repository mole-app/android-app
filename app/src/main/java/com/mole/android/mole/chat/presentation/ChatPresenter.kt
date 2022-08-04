package com.mole.android.mole.chat.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.chat.data.ChatDebtsData
import com.mole.android.mole.chat.model.ChatModel
import com.mole.android.mole.chat.view.ChatView
import kotlinx.coroutines.launch

class ChatPresenter(
    private val model: ChatModel,
    private val userId: Int
) : MoleBasePresenter<ChatView>() {
    private var isDataLoading = false
    private var debtLeft = -1
    private var idDebtMax: Int? = null

    override fun attachView(view: ChatView) {
        super.attachView(view)
        view.setToolbarLoading()
        view.showLoading()
        loadData(view, true)
    }

    fun onDebtsCreated() {
        if (!isDataLoading) {
            withView { view ->
                view.setToolbarLoading()
                view.showLoading()
                loadData(view, true)
            }
        }
    }

    fun onChatPreScrolledToTop() {
        if (!isDataLoading) {
            withView { view ->
                loadData(view, false)
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

    fun onFabViewClicked() {
        withView { view ->
            view.showCreateDebtScreen(userId)
        }
    }

    private fun loadData(
        view: ChatView,
        isLoadUserInfo: Boolean = false
    ) {
        isDataLoading = true
        withScope {
            launch {
                if (debtLeft != 0) {
                    model.loadChatData(userId, idDebtMax).withResult { result ->
                        debtLeft = result.debtLeft
                        idDebtMax = calculateLastDebtId(result.debts)
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

    private fun calculateLastDebtId(debts: List<ChatDebtsData>): Int? {
        return (debts[debts.size - 2] as? ChatDebtsData.ChatMessage)?.id
    }

    fun onDeleteItem(id: Int) {
        withScope {
            launch {
                model.deleteItem(id)
            }
        }
    }
}

package com.mole.android.mole.chat.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.chat.data.ChatDataDebtDomain
import com.mole.android.mole.chat.data.ChatDataDebtorDomain
import com.mole.android.mole.chat.data.ChatDebtsDataUi
import com.mole.android.mole.chat.model.ChatModel
import com.mole.android.mole.chat.view.ChatView
import kotlinx.coroutines.launch
import java.util.*

class ChatPresenter(
    private val model: ChatModel,
    private val userId: Int
) : MoleBasePresenter<ChatView>() {
    private var isDataLoading = false
    private var debtLeft = -1
    private var idDebtMax: Int? = null
    private val chatDebts: MutableList<ChatDataDebtDomain> = mutableListOf()
    private var chatDebtor: ChatDataDebtorDomain? = null
    private var lastChatDate: Date? = null
    private var isRetryBtnInViewHolder: Boolean = false

    override fun attachView(view: ChatView) {
        super.attachView(view)
        view.setToolbarLoading()
        view.showLoading()
        loadData(view)
    }

    override fun detachView() {
        super.detachView()
        debtLeft = -1
        idDebtMax = null
        chatDebts.clear()
        chatDebtor = null
        lastChatDate = null

    }

    fun onRetryClick() {
        if (!isDataLoading) {
            withView { view ->
                view.showLoading()
                loadData(view)
            }
        }
    }

    fun onChatPreScrolledToTop() {
        if (!isDataLoading) {
            withView { view ->
                loadData(view)
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

    fun onChatToolbarClicked() {
        withView { view ->
            chatDebtor?.let {
                view.showRepayScreen(it)
            }
        }
    }

    private fun loadData(
        view: ChatView
    ) {
        isDataLoading = true
        if (debtLeft == 0) {
            view.hideLoading()
            isDataLoading = false
            return
        }
        withScope {
            launch {
                model.loadChatData(userId, idDebtMax)
                    .withResult { result ->
                        view.hideError(isRetryBtnInViewHolder)
                        isRetryBtnInViewHolder = false
                        chatDebts.addAll(result.debts)
                        debtLeft = result.debtLeft
                        idDebtMax = chatDebts.last().id
                        if (chatDebtor == null) {
                            chatDebtor = result.debtor
                            view.setToolbarData(result.debtor)
                        }
                        view.setData(insertDateToChat(result.debts))
                        view.hideLoading()
                        isDataLoading = false
                    }
                    .withError {
                        isDataLoading = false
                        if (chatDebts.isNullOrEmpty()) {
                            view.showError()
                        } else {
                            if (!isRetryBtnInViewHolder) {
                                isRetryBtnInViewHolder = true
                                view.setData(listOf(ChatDebtsDataUi.RetryData))
                            }
                            view.showErrorToast()
                        }
                        view.hideLoading()
                    }
            }
        }
    }

    private fun insertDateToChat(debts: List<ChatDataDebtDomain>): List<ChatDebtsDataUi> {
        val debtsUi: MutableList<ChatDebtsDataUi> = mutableListOf()
        return if (debts.isNullOrEmpty()) {
            debtsUi
        } else {
            if (lastChatDate == null) {
                lastChatDate = debts[0].date
            }
            for (debt in debts) {
                val newDate = debt.date
                lastChatDate?.let { lastChatDate ->
                    if (isNewDate(newDate, lastChatDate)) {
                        debtsUi.add(ChatDebtsDataUi.ChatDate(lastChatDate))
                    }
                }
                lastChatDate = newDate
                val uiModel = if (debt.isRepay) {
                    ChatDebtsDataUi.RepayDebt(
                        id = debt.id,
                        userName = chatDebtor?.name.orEmpty(),
                        amount = debt.debtValue,
                        fromCurrentUser = debt.isMessageOfCreator
                    )
                } else {
                    ChatDebtsDataUi.ChatMessage(
                        id = debt.id,
                        isMessageOfCreator = debt.isMessageOfCreator,
                        debtValue = debt.debtValue,
                        tag = debt.tag,
                        isRead = debt.isRead,
                        isDeleted = debt.isDeleted,
                        remoteTime = debt.date
                    )
                }
                debtsUi.add(uiModel)
            }
            lastChatDate?.let { lastChatDate ->
                if (debtLeft == 0) {
                    debtsUi.add(ChatDebtsDataUi.ChatDate(lastChatDate))
                }
            }
            debtsUi
        }
    }

    private fun isNewDate(newDate: Date, oldDate: Date): Boolean {
        val newDateWithoutTime = removeTime(newDate)
        val oldDateWithoutTime = removeTime(oldDate)
        return (oldDateWithoutTime.time - newDateWithoutTime.time >= MILLIS_IN_DAY)

    }

    private fun removeTime(date: Date): Date {
        val currentTime = date.time
        val dateInMillisWithoutTime = (currentTime / MILLIS_IN_DAY) * MILLIS_IN_DAY
        return Date(dateInMillisWithoutTime)

    }

    companion object {
        private const val MILLIS_IN_DAY = (60 * 60 * 24 * 1000).toLong()
    }

    fun onDeleteItem(id: Int) {
        withScope {
            launch {
                model.deleteItem(id)
            }
        }
    }
}

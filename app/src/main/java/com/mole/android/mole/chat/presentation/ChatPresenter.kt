package com.mole.android.mole.chat.presentation

import com.github.terrakok.cicerone.Router
import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.chat.model.ChatModel
import com.mole.android.mole.chat.view.ChatView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ChatPresenter(
    private val model: ChatModel,
    private val router: Router,
    private val scope: CoroutineScope
) : MoleBasePresenter<ChatView>() {
    override fun attachView(view: ChatView) {
        super.attachView(view)
        scope.launch {
            model.getChatData().withResult { result ->
                view.setData(result)
            }
        }
    }
}
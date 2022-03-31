package com.mole.android.mole.di

import com.mole.android.mole.chat.model.ChatModel
import com.mole.android.mole.chat.model.ChatModelImplementation
import com.mole.android.mole.chat.presentation.ChatPresenter

class ChatModule(
    private val routingModule: RoutingModule,
    private val baseScopeModule: BaseScopeModule
) {
    private val chatModel: ChatModel by lazy {
        ChatModelImplementation()
    }

    val chatPresenter = ChatPresenter(chatModel, routingModule.router, baseScopeModule.mainScope)
}
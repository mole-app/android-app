package com.mole.android.mole.di

import com.mole.android.mole.chat.model.ChatModel
import com.mole.android.mole.chat.model.ChatModelImplementation
import com.mole.android.mole.chat.presentation.ChatPresenter

class ChatModule(
    private val baseScopeModule: BaseScopeModule
) {
    val chatPresenter
        get() = ChatPresenter(chatModel, baseScopeModule.mainScope)

    private val chatModel: ChatModel by lazy {
        ChatModelImplementation(
            baseScopeModule.mainScope
        )
    }
}
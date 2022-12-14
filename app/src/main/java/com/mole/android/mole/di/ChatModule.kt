package com.mole.android.mole.di

import com.mole.android.mole.chat.model.ChatModel
import com.mole.android.mole.chat.model.ChatModelImplementation
import com.mole.android.mole.chat.model.ChatService
import com.mole.android.mole.chat.model.MockedChatModel
import com.mole.android.mole.chat.presentation.ChatPresenter

class ChatModule(
    retrofitModule: RetrofitModule,
    private val baseScopeModule: BaseScopeModule
) {

    fun chatPresenter(id: Int): ChatPresenter{
        return ChatPresenter(chatModel, id)
    }

    private val chatModel: ChatModel by lazy {
        ChatModelImplementation(
            chatService,
            baseScopeModule.ioScope
        )
//        MockedChatModel(baseScopeModule.ioScope)
    }

    private val chatService by lazy {
        retrofitModule.retrofit.create(ChatService::class.java)
    }
}
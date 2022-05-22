package com.mole.android.mole.chat.data

data class ChatDataDebt(
    val id: Int,
    val sum: Int,
    val idUser: Int,
    val idCreator: Int,
    val isDelete: Boolean,
    val debtType: String,
    val createTime: String,
    val tag: String
)

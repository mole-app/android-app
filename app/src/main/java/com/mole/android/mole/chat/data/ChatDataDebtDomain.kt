package com.mole.android.mole.chat.data

import com.google.gson.annotations.SerializedName

data class ChatDataDebtDomain(
    @SerializedName("id")
    val id: Int,
    @SerializedName("sum")
    val sum: Int,
    @SerializedName("idUser")
    val idUser: Int,
    @SerializedName("idCreator")
    val idCreator: Int,
    @SerializedName("isDelete")
    val isDelete: Boolean,
    @SerializedName("debtType")
    val debtType: String,
    @SerializedName("createTime")
    val createTime: String,
    @SerializedName("tag")
    val tag: String?
)

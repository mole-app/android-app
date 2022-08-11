package com.mole.android.mole.chat.data

import com.google.gson.annotations.SerializedName

enum class DebtType {
    @SerializedName("Give")
    GIVE,
    @SerializedName("Get")
    GET
}
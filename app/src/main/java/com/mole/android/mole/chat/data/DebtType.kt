package com.mole.android.mole.chat.data

import com.google.gson.annotations.SerializedName

enum class DebtType(val stringValue: String) {
    @SerializedName("Give")
    GIVE("Give"),
    @SerializedName("Get")
    GET("Get")
}
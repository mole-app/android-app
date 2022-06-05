package com.mole.android.mole.create.data

interface CreateDebtsDataRepository {
    fun userId(): Int
    fun tag(): String
    fun side(): Boolean
}
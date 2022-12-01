package com.mole.android.mole.repay.model

import kotlinx.coroutines.CoroutineScope

class RepayModelImplementation(
    private val service: RepayService,
     private val scope: CoroutineScope
): RepayModel {
    override fun repayAmount() {
        TODO("Not yet implemented")
    }
}
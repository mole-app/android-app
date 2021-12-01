package com.mole.android.mole.debts.presentation
import com.github.terrakok.cicerone.Router
import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.debts.data.DebtsData
import com.mole.android.mole.debts.model.DebtsModel
import com.mole.android.mole.debts.view.DebtsView
import kotlinx.coroutines.CoroutineScope

class DebtsPresenter(
    private val model: DebtsModel,
    private val router: Router,
    private val scope: CoroutineScope
): MoleBasePresenter<DebtsView>() {

    fun getData(): List<DebtsData>{
        return model.getDebtsData()
    }

    fun onLongChatClick(){}

    fun onShortChatClick(){}
}
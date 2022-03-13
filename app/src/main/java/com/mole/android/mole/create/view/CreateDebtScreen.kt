package com.mole.android.mole.create.view

import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.R
import com.mole.android.mole.component
import com.mole.android.mole.create.view.chooseside.ChooseSideScreen
import com.mole.android.mole.create.view.steps.CreateStepsScreen
import com.mole.android.mole.databinding.FragmentCreateDebtBinding

class CreateDebtScreen : MoleBaseFragment<FragmentCreateDebtBinding>(FragmentCreateDebtBinding::inflate) {

    private val router = component().routingModule.router
    private val navigatorHolder = component().routingModule.navigationHolder

    override fun getNavigator(): Navigator {
        return AppNavigator(requireActivity(), R.id.create_debt_host)
    }

    override fun onResume() {
        super.onResume()
        router.newChain(Screens.ChooseSide())
    }

    internal object Screens {
        fun ChooseSide() = FragmentScreen { ChooseSideScreen() }
        fun CreateSteps() = FragmentScreen { CreateStepsScreen() }
    }

}
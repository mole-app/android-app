package com.mole.android.mole.create.view

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
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

    override fun getNavigator(): Navigator {
        return AppNavigator(
            requireActivity(),
            R.id.create_debt_host,
            fragmentManager = childFragmentManager
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun onStart() {
        super.onStart()
        router.newChain(Screens.ChooseSide())
    }

    override fun onStop() {
        super.onStop()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    internal object Screens {
        fun ChooseSide() = FragmentScreen { ChooseSideScreen() }
        fun CreateSteps() = FragmentScreen { CreateStepsScreen() }
    }

}
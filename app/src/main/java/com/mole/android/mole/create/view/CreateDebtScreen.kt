package com.mole.android.mole.create.view

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

    private val childNavigator by lazy {
        AppNavigator(
            requireActivity(),
            R.id.create_debt_host,
            fragmentManager = childFragmentManager
        )
    }

    private val navigatorHolder = component().routingModule.navigationHolder

    override fun getNavigator(): Navigator {
        return AppNavigator(requireActivity(), R.id.nav_host_fragment)
    }

    override fun onStart() {
        super.onStart()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        withChildNavigation {
            router.newChain(Screens.ChooseSide())
        }
    }

    override fun onStop() {
        super.onStop()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    private fun withChildNavigation(action: () -> Unit) {
        navigatorHolder.setNavigator(childNavigator)
        action()
        navigatorHolder.setNavigator(getNavigator())
    }

    internal object Screens {
        fun ChooseSide() = FragmentScreen { ChooseSideScreen() }
        fun CreateSteps() = FragmentScreen { CreateStepsScreen() }
    }

}
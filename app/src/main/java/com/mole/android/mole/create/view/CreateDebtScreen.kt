package com.mole.android.mole.create.view

import android.os.Bundle
import android.view.View
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
import com.mole.android.mole.setResultListenerGeneric

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        withChildNavigation {
            val id = arguments?.getInt(EXTRA_ID, -1)
            router.newChain(Screens.ChooseSide(id ?: -1))
        }
        router.setResultListenerGeneric<Int>(CreateStepsScreen.CREATE_STEPS_EXTRA_ID_RESULT_KEY) {
            router.sendResult(CREATED_DEBT_ID_KEY, id)
            router.exit()
        }
    }

    private fun withChildNavigation(action: () -> Unit) {
        navigatorHolder.setNavigator(childNavigator)
        action()
        navigatorHolder.setNavigator(getNavigator())
    }

    internal object Screens {
        fun ChooseSide(id: Int) = FragmentScreen { ChooseSideScreen.instance(id) }
        fun CreateSteps(side: Boolean, id: Int) = FragmentScreen {
            CreateStepsScreen.instance(side, id)
        }
    }

    companion object {
        fun instance(id: Int = -1): CreateDebtScreen {
            return CreateDebtScreen().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_ID, id)
                }
            }
        }
        private const val EXTRA_ID = "create_debt_screen_extra_id"
        const val CREATED_DEBT_ID_KEY = "created_debt_id_key"
    }

}
package com.mole.android.mole.create.view.chooseside

import android.os.Bundle
import android.view.View
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.R
import com.mole.android.mole.component
import com.mole.android.mole.create.view.CreateDebtScreen
import com.mole.android.mole.databinding.FragmentChooseSideBinding
import com.mole.android.mole.ui.actionbar.MoleActionBar

class ChooseSideScreen : MoleBaseFragment<FragmentChooseSideBinding>(FragmentChooseSideBinding::inflate), ChooseSideView {

    private val presenter = component().createDebtsModule.chooseSidePresenter
    private val router = component().routingModule.router

    override fun getToolbar(): MoleActionBar {
        return binding.actionBar
    }

    override fun getNavigator(): Navigator {
        return AppNavigator(requireActivity(), R.id.create_debt_host)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createDebtPositiveButton.apply {
            render(ChooseSideCardView.Side.HIS_DEBT)
            setOnClickListener { presenter.onHisDebtClicked() }
        }
        binding.createDebtNegativeButton.apply {
            render(ChooseSideCardView.Side.MY_DEBT)
            setOnClickListener { presenter.onMyDebtClicked() }
        }

        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun openNextScreen(side: Boolean) {
        router.sendResult(EXTRA_SIDE, side)
    }

    companion object {
        fun instance(id: Int): ChooseSideScreen {
            return ChooseSideScreen().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_ID, id)
                }
            }
        }

        private const val EXTRA_ID = "choose_side_screen_extra_id"
        const val EXTRA_SIDE = "choose_side_screen_extra_id"
    }

}
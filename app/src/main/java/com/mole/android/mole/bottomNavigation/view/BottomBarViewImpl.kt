package com.mole.android.mole.bottomNavigation.view

import android.os.Bundle
import android.view.View
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.google.android.material.snackbar.Snackbar
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.R
import com.mole.android.mole.bottomNavigation.presentation.BottomBarPresenter
import com.mole.android.mole.component
import com.mole.android.mole.create.view.CreateDebtScreen
import com.mole.android.mole.databinding.FragmentWithBotnavBinding
import com.mole.android.mole.debts.view.DebtsViewImplementation
import com.mole.android.mole.profile.view.ProfileViewImpl
import com.mole.android.mole.resolveColor

class BottomBarViewImpl private constructor() :
    MoleBaseFragment<FragmentWithBotnavBinding>(FragmentWithBotnavBinding::inflate), BottomBarView {

    private val presenter: BottomBarPresenter = BottomBarPresenter()

    private var currentFragmentTag = DEBTS_TAG
    private val router = component().routingModule.router
    private val navigatorHolder = component().routingModule.navigationHolder

    override fun getNavigator(): Navigator {
        return AppNavigator(requireActivity(), R.id.nav_host_fragment)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(FRAGMENT_TAG_KEY, currentFragmentTag)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentFragmentTag =
            arguments?.getString(FRAGMENT_ID) ?: savedInstanceState?.getString(FRAGMENT_TAG_KEY)
                    ?: DEBTS_TAG

        binding.moleBottomNavigationBar.setOnFabClickListener {
            presenter.onNewDebtClick()
        }

        binding.moleBottomNavigationBar.setOnNavigationItemSelectedListener { item ->
            navigatorHolder.setNavigator(getNavigator())
            if (!item.isChecked) {
                when (item.itemId) {
                    R.id.navigation_debts -> {
                        presenter.onDebtsClick()
                    }
                    R.id.navigation_profile -> {
                        presenter.onProfileClick()
                    }
                }
            }
            true
        }

        binding.moleBottomNavigationBar.setOnFabClickListener {
            navigatorHolder.setNavigator(AppNavigator(requireActivity(), R.id.fragment_container))
            router.navigateTo(Screens.CreateDebt())
        }
        presenter.attachView(this)
    }

    override fun onResume() {
        super.onResume()
        when (currentFragmentTag) {
            DEBTS_TAG -> {
                router.newRootScreen(Screens.Debts())
            }
            PROFILE_TAG -> {
                router.newChain(Screens.Profile())
            }
        }
    }

    companion object {
        private const val DEBTS_TAG = "DEBTS_TAG"
        private const val PROFILE_TAG = "PROFILE_TAG"
        private const val FRAGMENT_TAG_KEY = "FRAGMENT_KEY"
        private const val FRAGMENT_ID = "FRAGMENT_ID"

        fun withDebts(): BottomBarViewImpl {
            return with(DEBTS_TAG)
        }

        fun withProfile(): BottomBarViewImpl {
            return with(PROFILE_TAG)
        }

        private fun with(tag: String): BottomBarViewImpl {
            val args = Bundle()
            args.putString(FRAGMENT_ID, tag)
            val fragment = BottomBarViewImpl()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onBackPress(): Boolean {
        binding.moleBottomNavigationBar.setSelectedItem(R.id.navigation_debts)
        return false
    }

    private object Screens {
        fun Debts() = FragmentScreen { DebtsViewImplementation() }
        fun Profile() = FragmentScreen { ProfileViewImpl() }
        fun CreateDebt() = FragmentScreen { CreateDebtScreen() }
    }

    override fun openDebts() {
        arguments?.putString(FRAGMENT_ID, DEBTS_TAG)
        router.navigateTo(Screens.Debts())
        currentFragmentTag = DEBTS_TAG
    }

    override fun openProfile() {
        arguments?.putString(FRAGMENT_ID, PROFILE_TAG)
        router.newChain(Screens.Profile())
        currentFragmentTag = PROFILE_TAG
    }

    override fun openNewDebt() {
        val snakbar = Snackbar.make(
            binding.root.findViewById(R.id.snackbarHolder),
            "message",
            Snackbar.LENGTH_SHORT
        )
        snakbar.setBackgroundTint(requireContext().resolveColor(R.attr.colorOnSurface))
        snakbar.show()

    }

    override fun getViewUnderSnackbar(): View {
        return binding.moleBottomNavigationBar.fab
    }
}
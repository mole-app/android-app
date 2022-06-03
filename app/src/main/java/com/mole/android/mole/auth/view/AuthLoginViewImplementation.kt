package com.mole.android.mole.auth.view

import android.os.Bundle
import android.view.View
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mole.android.mole.*
import com.mole.android.mole.auth.data.AuthDataLogin
import com.mole.android.mole.auth.presentation.AuthLoginPresenter
import com.mole.android.mole.databinding.ViewAuthLoginBinding
import com.mole.android.mole.navigation.Screens


class AuthLoginViewImplementation :
    MoleBaseFragment<ViewAuthLoginBinding>(ViewAuthLoginBinding::inflate), AuthLoginView {

    private lateinit var login: String

    private val router = component().routingModule.router

    companion object {
        private const val LOGIN_ID = "login_id"
        fun newInstance(login: String): AuthLoginViewImplementation {
            val args = Bundle()
            args.putSerializable(LOGIN_ID, login)
            val fragment = AuthLoginViewImplementation()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var presenter: AuthLoginPresenter

    override fun showLoginExistError() {
        binding.authLogo.error = getString(R.string.login_exist_error)
    }

    override fun hideError() {
        binding.authLogo.error = null
    }

    override fun setUserLogin(login: String) {
        binding.authLogo.editText?.setText(resources.getString(R.string.login_prefix, login))
    }

    override fun openDebts() {
        router.newRootScreen(Screens.Debts())
    }

    override fun getToolbar() = binding.moleAuthToolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login = arguments?.getString(LOGIN_ID).toString()

        binding.authLogo.editText?.onTextChanged { charSequence ->
            presenter.onTextChanged(charSequence)
        }

        binding.authButton.setOnClickListener {
            presenter.onFabClick()
        }

        binding.authButton.setBorder(
            Shape.OVAL,
            16f.dp()
        )

        presenter =
            component().authModule.loginPresenter(
                AuthDataLogin(
                    "",
                    "",
                    "",
                    login
                )
            )

        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}

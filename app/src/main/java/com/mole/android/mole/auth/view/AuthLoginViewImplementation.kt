package com.mole.android.mole.auth.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputLayout
import com.mole.android.mole.*
import com.mole.android.mole.auth.data.AuthDataLogin
import com.mole.android.mole.auth.presentation.AuthLoginPresenter
import com.mole.android.mole.ui.actionbar.MoleActionBar


class AuthLoginViewImplementation : MoleBaseFragment(), AuthLoginView {

    private lateinit var login: String
    private var toolbar: MoleActionBar? = null

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

    private lateinit var textInputLayout: TextInputLayout

    override fun showLoginExistError() {
        textInputLayout.error = getString(R.string.login_exist_error)
    }

    override fun hideError() {
        textInputLayout.error = null
    }

    override fun setUserLogin(login: String) {
        textInputLayout.editText?.setText(login)
    }

    override fun getToolbar(): Toolbar? {
        return toolbar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar = view.findViewById(R.id.mole_auth_toolbar)
        super.onViewCreated(view, savedInstanceState)

        login = arguments?.getString(LOGIN_ID).toString()

        val button: AppCompatImageButton = view.findViewById(R.id.auth_button)
        textInputLayout = view.findViewById(R.id.auth_logo)

        textInputLayout.editText?.onTextChanged { charSequence ->
            presenter.onTextChanged(charSequence)
        }

        button.setOnClickListener {
            presenter.onFabClick()
        }

        button.setBorder(
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.view_auth_login, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}

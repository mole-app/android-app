package com.mole.android.mole.auth.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputLayout
import com.mole.android.mole.*
import com.mole.android.mole.auth.model.AuthModel
import com.mole.android.mole.auth.presentation.AuthPresenter
import com.mole.android.mole.ui.actionbar.MoleActionBar


class AuthLoginFragmentImplementation : MoleBaseFragment(), AuthLoginFragment {

    private var toolbar: MoleActionBar? = null

    private val presenter: AuthPresenter
    private lateinit var textInputLayout: TextInputLayout

    init {
        val model = AuthModel()
        presenter = AuthPresenter(model)
    }

    override fun showError(error: String) {
        textInputLayout.error = error
    }

    override fun hideError() {
        textInputLayout.error = null
    }

    override fun getUserLogin(): String {
        return textInputLayout.editText?.text.toString()
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

        val button: AppCompatImageButton = view.findViewById(R.id.auth_button)
        textInputLayout = view.findViewById(R.id.auth_logo)

        textInputLayout.editText?.beforeTextChanged {
            presenter.textChanged()
        }

        button.setOnClickListener {
            presenter.onFabClick()
        }


        button.setBorder(
            Shape.OVAL,
            16f.dp(),
            1f.dp(),
            R.attr.colorIconDisabled,
            R.attr.colorGradientStroke
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

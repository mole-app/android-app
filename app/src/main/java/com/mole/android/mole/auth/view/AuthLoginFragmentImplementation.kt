package com.mole.android.mole.auth.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.mole.android.mole.R
import com.mole.android.mole.Shape
import com.mole.android.mole.auth.model.AuthModel
import com.mole.android.mole.auth.presentation.AuthPresenter
import com.mole.android.mole.dp
import com.mole.android.mole.setBorder
import com.mole.android.mole.ui.actionbar.MoleActionBar


class AuthLoginFragmentImplementation : Fragment(), AuthLoginFragment {

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.view_auth_login, container, false)

        toolbar = view.findViewById(R.id.mole_auth_toolbar)
        val button: AppCompatImageButton = view.findViewById(R.id.auth_button)
        textInputLayout = view.findViewById(R.id.auth_logo)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)


        textInputLayout.editText?.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    presenter.textChanged(s, start, before, count)
                }
            }
        )

        button.setOnClickListener {
            presenter.nextFragment()
        }


        button.setBorder(
            Shape.OVAL,
            16f.dp(),
            1f.dp(),
            R.attr.colorIconDisabled,
            R.attr.colorGradientStroke
        )

        presenter.attachView(this)

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}

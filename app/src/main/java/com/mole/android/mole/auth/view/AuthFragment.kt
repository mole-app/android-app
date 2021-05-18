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
import com.mole.android.mole.auth.model.AuthModel
import com.mole.android.mole.auth.presentation.AuthPresentation
import com.mole.android.mole.ui.actionbar.MoleActionBar


class AuthFragment : Fragment() {

    private var toolbar: MoleActionBar? = null

    private val model = AuthModel()
    private val presenter = AuthPresentation(model)
    private lateinit var textInputLayout: TextInputLayout

    init {
        presenter.attachView(this)
    }

    fun showError(error: String) {
        textInputLayout.error = error
    }

    fun hideError() {
        textInputLayout.error = null
    }

    fun getUserLogin(): String {
        return textInputLayout.editText?.text.toString()
    }

    fun setUserLogin(login: String) {
        textInputLayout.editText?.setText(login)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.view_auth_login, container, false)

        toolbar = view.findViewById(R.id.mole_auth_toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        textInputLayout = view.findViewById(R.id.auth_logo)

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

        val button: AppCompatImageButton = view.findViewById(R.id.auth_button)
        button.setOnClickListener {
            presenter.nextFragment()
        }

        presenter.viewIsReady()

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}

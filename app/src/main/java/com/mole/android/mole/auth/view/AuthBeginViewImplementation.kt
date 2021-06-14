package com.mole.android.mole.auth.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.github.terrakok.cicerone.Router
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.R
import com.mole.android.mole.auth.model.AuthModelImplementation
import com.mole.android.mole.auth.presentation.AuthBeginPresenter
import com.mole.android.mole.component

class AuthBeginViewImplementation :
    MoleBaseFragment(), AuthBeginView {

    private val presenter = component().authModule.beginPresenter

    private lateinit var client: GoogleSignInClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vkButton: AppCompatButton = view.findViewById(R.id.vk_button)

        vkButton.setOnClickListener {
            presenter.onVkClick()
        }

        val googleButton: AppCompatButton = view.findViewById(R.id.google_button)

        googleButton.setOnClickListener {
            presenter.onGoogleClick()
        }

        presenter.attachView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("866261272024-9oc61vo2mfgci38pm9duk2d480gljlap.apps.googleusercontent.com")
            .requestEmail()
            .build()

        client = GoogleSignIn.getClient(requireActivity(), gso)

        return inflater.inflate(R.layout.view_auth_begin, container, false)
    }
}
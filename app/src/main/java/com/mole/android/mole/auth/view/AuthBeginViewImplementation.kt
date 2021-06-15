package com.mole.android.mole.auth.view

import android.app.Activity
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.R
import com.mole.android.mole.component


class AuthBeginViewImplementation :
    MoleBaseFragment(), AuthBeginView {

    private val presenter = component().authModule.beginPresenter

    private lateinit var client: GoogleSignInClient
    override lateinit var googleAccount: GoogleSignInAccount

    companion object {
        const val CODE_SIGN = 1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vkButton: AppCompatButton = view.findViewById(R.id.vk_button)

        vkButton.setOnClickListener {
            presenter.onVkClick()
        }

        val googleButton: AppCompatButton = view.findViewById(R.id.google_button)

        googleButton.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("866261272024-9oc61vo2mfgci38pm9duk2d480gljlap.apps.googleusercontent.com")
                .requestEmail()
                .build()

            val client = GoogleSignIn.getClient(requireActivity(), gso)
            startActivityForResult(client.signInIntent, CODE_SIGN)
        }

        presenter.attachView(this)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            googleAccount = completedTask.getResult(ApiException::class.java)!!
            Toast.makeText(requireContext(), "You signed in", Toast.LENGTH_SHORT).show()
            presenter.onGoogleClick()
        } catch (e: ApiException) {
            Log.w("GoogleAuth", "signInResult:failed code=" + e.statusCode)
            Toast.makeText(requireContext(), "Error signed!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_SIGN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
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
package com.mole.android.mole.auth.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.mole.android.mole.*
import com.mole.android.mole.auth.view.AuthWebViewImpl.Companion.CODE_SIGN
import com.mole.android.mole.databinding.ViewAuthBeginBinding
import com.mole.android.mole.di.RetrofitModule
import com.mole.android.mole.navigation.Screens


class AuthBeginViewImplementation :
    MoleBaseFragment<ViewAuthBeginBinding>(ViewAuthBeginBinding::inflate), AuthBeginView {

    private val presenter = component().authModule.beginPresenter
    private val router = component().routingModule.router
    private val remoteConfig = component().remoteConfigModule.remoteConfig

    private lateinit var client: GoogleSignInClient
    override lateinit var googleAccount: GoogleSignInAccount
    override fun openAuthLogin(login: String) {
        router.replaceScreen(Screens.AuthLogin(login))
    }

    override fun openDebts() {
        router.newRootScreen(Screens.Debts())
    }

    override fun openBrowser(actionAfter: (String) -> Unit) {
        router.setResultListenerGeneric(CODE_SIGN, actionAfter)
        router.navigateTo(Screens.AuthBrowser(RetrofitModule.VK_URL))
    }

    private val mainActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }

    override fun getViewUnderSnackbar(): View {
        return binding.vkButton
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("866261272024-9oc61vo2mfgci38pm9duk2d480gljlap.apps.googleusercontent.com")
            .requestEmail()
            .build()

        client = GoogleSignIn.getClient(requireActivity(), gso)

        binding.vkButton.setOnClickListener {
            presenter.onVkClick()
        }

        if (remoteConfig.getGoogleEnable()) {
            binding.googleButton.setOnClickListener {
                val intent = client.signInIntent
                mainActivityResultLauncher.launch(intent)
            }
        } else {
            binding.googleButton.visibility = GONE
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
}
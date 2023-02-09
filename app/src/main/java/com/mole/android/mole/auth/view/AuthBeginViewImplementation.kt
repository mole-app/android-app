package com.mole.android.mole.auth.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
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

    override lateinit var googleAccount: GoogleSignInAccount
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

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

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
            }
        }

    override fun getViewUnderSnackbar(): View {
        return binding.vkButton
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vkButton.setOnClickListener {
            if (isNetworkConnected(requireContext())) {
                presenter.onVkClick()
            } else {
                Toast.makeText(requireContext(), R.string.loading_error, Toast.LENGTH_SHORT).show()
            }
        }

        binding.googleButton.setOnClickListener {
            if (isNetworkConnected(requireContext())) {
                val signInIntent = googleSignInClient.signInIntent
                launcher.launch(signInIntent)
            } else {
                Toast.makeText(requireContext(), R.string.loading_error, Toast.LENGTH_SHORT).show()
            }
        }

//        if (remoteConfig.getGoogleEnable()) {
//            binding.googleButton.setOnClickListener {}
//        } else {
//            binding.googleButton.visibility = GONE
//        }
        view.onMeasured {
            val topEllipse = binding.topEllipse
            val bottomEllipse = binding.bottomEllipse
            val centerLogo = binding.logo

            val logoCenterX = centerLogo.centerX()
            val logoCenterY = centerLogo.centerY()

            val fadeDuration = 300L
            val translationDuration = 500L
            val waitDuration = 300L

            centerLogo.alpha = 0f

            topEllipse.animate()
                .translationX(logoCenterX - topEllipse.x)
                .translationY(logoCenterY - topEllipse.bottomY())
                .setDuration(translationDuration).start()

            bottomEllipse.animate()
                .translationX(logoCenterX - bottomEllipse.endX() + 20.dp)
                .translationY(logoCenterY - bottomEllipse.y - 52.dp)
                .setDuration(translationDuration).start()

            view.postDelayed({
                centerLogo.animate().alpha(1f).setDuration(fadeDuration).start()
            }, waitDuration)
        }

        presenter.attachView(this)
    }

    private fun View.endX(): Float {
        return x + measuredWidth
    }

    private fun View.bottomY(): Float {
        return y + measuredHeight
    }

    private fun View.centerX(): Float {
        return x + measuredWidth / 2
    }

    private fun View.centerY(): Float {
        return y + measuredHeight / 2
    }

    private fun View.onMeasured(action: () -> Unit) {
        viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver
                    .removeOnGlobalLayoutListener(this)
                action()
            }
        })
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            googleAccount = completedTask.getResult(ApiException::class.java)!!
            presenter.onGoogleClick()
        } catch (e: ApiException) {
            Log.w("GoogleAuth", "signInResult:failed code=" + e.statusCode)
        }
    }
}
package com.mole.android.mole.auth.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.github.terrakok.cicerone.Router
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.component
import com.mole.android.mole.databinding.WebViewFragmentBinding

class AuthWebViewImpl : MoleBaseFragment<WebViewFragmentBinding>(WebViewFragmentBinding::inflate) {

    private lateinit var url: String
    private val router: Router = component().routingModule.router

    companion object {
        private const val URL_ID = "url_id"
        fun newInstance(url: String): AuthWebViewImpl {
            val args = Bundle()
            args.putSerializable(URL_ID, url)
            val fragment = AuthWebViewImpl()
            fragment.arguments = args
            return fragment
        }

        const val CODE_SIGN = "auth_begin_view_with_vk"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        url = arguments?.getString(URL_ID).toString()
        Log.i("Auth", "Argument url: $url")

        binding.webView.loadUrl(url)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                val uri = request.url
                Log.i("Auth", "url: $uri")
                if (uri.authority == "mole-app.ru") {
                    val code = uri.getQueryParameter("code")
                    if (code != null) {
                        router.sendResult(CODE_SIGN, code)
                    }
                    router.exit()
                    return true
                }
                return false
            }
        }
    }
}
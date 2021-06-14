package com.mole.android.mole.auth.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.github.terrakok.cicerone.Router
import com.mole.android.mole.MoleBaseFragment
import com.mole.android.mole.R
import java.util.*

class AuthWebViewImpl(private val url: String, val router: Router) : MoleBaseFragment() {

//    companion object {
//        private const val TAG = "CrimeFragment"
//        private const val ARG_CRIME_MODE = "crime_mode"
//        private const val ARG_CRIME_ID = "crime_id"
//        private const val DIALOG_DATE = "DialogDate"
//        fun newInstance(url: String, router: Router): AuthWebViewImpl {
//            val args = Bundle()
//            args.putSerializable(ARG_CRIME_ID, url)
//            args.putSerializable(ARG_CRIME_MODE, router)
//            val fragment = AuthWebViewImpl()
//            fragment.arguments = args
//            return fragment
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webView: WebView = view.findViewById(R.id.web_view)
        webView.loadUrl(url)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                val uri = request.url
                Log.i("Auth", "url: $uri")
                if (uri.authority == "mole-app.ru") {
                    val code = uri.getQueryParameter("code")
                    if (code != null) {
                        router.sendResult("code", code)
                    }
                    router.exit()
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.web_view_fragment, container, false)
    }

}
package zojae031.portfolio.presentation.ui.tec

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_tec.*
import zojae031.portfolio.R

class TecActivity : Activity() {

    private val url by lazy { intent.getStringExtra(EXTRA_DATA) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tec)
        setWebView()
    }

    private fun setWebView() {
        web_view.apply {
            settings.javaScriptEnabled = true
            webChromeClient = WebChromeClient()
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url)
                    return false
                }
            }
        }.also {
            if (url != "")
                it.loadUrl(url)
        }
    }

    companion object {
        private const val EXTRA_DATA = "EXTRA_DATA"
        fun getIntent(context: Context, data: String): Intent =
            Intent(context, TecActivity::class.java).apply {
                putExtra(
                    EXTRA_DATA, data
                )
            }
    }
}
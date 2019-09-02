package zojae031.portfolio.tec

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_tec.*
import zojae031.portfolio.R
import zojae031.portfolio.data.dao.tec.TecEntity

class TecActivity : Activity() {
    private val datas by lazy { intent.getSerializableExtra(EXTRA_DATA) as TecEntity }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tec)

        setWebView()
    }


    private fun setWebView() {
        web_view.settings.javaScriptEnabled = true
        web_view.webChromeClient = WebChromeClient()
        web_view.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return false
            }
        }
        web_view.loadUrl(datas.source?.get(0)?.asJsonObject?.get("data1")?.asString)
    }

    companion object {
        private const val EXTRA_DATA = "EXTRA_DATA"
        fun getIntent(context: Context, data: TecEntity): Intent =
            Intent(context, TecActivity::class.java).apply { putExtra(EXTRA_DATA, data) }

    }
}
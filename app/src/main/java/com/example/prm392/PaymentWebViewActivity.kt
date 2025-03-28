package com.example.prm392
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class PaymentWebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_webview)

        val webView = findViewById<WebView>(R.id.webView)
        val paymentUrl = intent.getStringExtra("paymentUrl") ?: return finish()

        // Configure WebView
        webView.settings.javaScriptEnabled = true // Enable JavaScript if required by your payment page
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                val uri = Uri.parse(url)
                // Check for success or cancel URLs
                if (url != null && (url.contains("success") || url.contains("cancel"))) {
                    val orderCode = uri.getQueryParameter("orderCode") ?: ""
                    val orderStatus = uri.getQueryParameter("status") ?: "CANCELLED"
                    // Return result to MainActivity
                    val resultIntent = Intent().apply {
                        putExtra("orderCode", orderCode)
                        putExtra("orderStatus", orderStatus)
                    }
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                    return true
                }
                return false // Let WebView handle the URL normally
            }
        }

        // Load the payment URL
        webView.loadUrl(paymentUrl)
    }
}
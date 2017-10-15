package blitzboba.blitzboba;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import blitzboba.blitzbobav2.R;

/**
 * Created by Rodrigo on 10/9/2017.
 */

public class BlitzOrderingWebView extends Activity {
    private WebView webView;
    private ProgressDialog progDailog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //TODO keep in case needed to externalize to activity
        super.onCreate(savedInstanceState);

        setContentView(R.layout.blitz_order_webview);

        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        progDailog = ProgressDialog.show(this, "Loading","Please wait...", true);
        progDailog.setCancelable(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                progDailog.show();
                webView.loadUrl("https://squareup.com/store/blitzboba");
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progDailog.dismiss();
            }
        });

        webView.loadUrl("https://squareup.com/store/blitzboba");
    }
}

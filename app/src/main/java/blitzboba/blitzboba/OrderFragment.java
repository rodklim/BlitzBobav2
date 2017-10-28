package blitzboba.blitzboba;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import blitzboba.blitzbobav2.R;

/**
 * Created by Rodrigo on 10/10/2017.
 */

public class OrderFragment extends Fragment {

    private WebView webView;
    private ProgressDialog progDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.blitz_order_webview, container, false);


        webView = (WebView) v.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);
//        progDialog = ProgressDialog.show(getActivity(), "Loading", "Please wait...", true);
//        progDialog.setCancelable(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                progDialog.show();
                webView.loadUrl("https://squareup.com/store/blitzboba");
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                progDialog.dismiss();
            }
        });

        webView.loadUrl("https://squareup.com/store/blitzboba");

        return v;
    }
}

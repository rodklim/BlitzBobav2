package blitzboba.blitzboba;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.util.Date;

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
        //TODO need to clear cache
        super.onCreate(savedInstanceState);

        setContentView(R.layout.blitz_order_webview);

        webView = (WebView) findViewById(R.id.webView);
        final WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setSaveFormData(false);
        getApplicationContext().deleteDatabase("webview.db");
        getApplicationContext().deleteDatabase("webviewCache.db");

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
                webView.clearCache(true);
                webView.clearFormData();
                webView.reload();
            }
        });

        webView.loadUrl("https://squareup.com/store/blitzboba");

    }

    static int clearCacheFolder(final File dir, final int numDays) {

        int deletedFiles = 0;
        if (dir!= null && dir.isDirectory()) {
            try {
                for (File child:dir.listFiles()) {

                    //first delete subdirectories recursively
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, numDays);
                    }

                    //then delete the files and subdirectories in this dir
                    //only empty directories can be deleted, so subdirs have been done first
                    if (child.lastModified() < new Date().getTime() - numDays * DateUtils.DAY_IN_MILLIS) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            }
            catch(Exception e) {
                Log.e("rlim", String.format("Failed to clean the cache, error %s", e.getMessage()));
            }
        }
        return deletedFiles;
    }

    /*
     * Delete the files older than numDays days from the application cache
     * 0 means all files.
     */
    public static void clearCache(final Context context, final int numDays) {
        Log.i("rlim", String.format("Starting cache prune, deleting files older than %d days", numDays));
        int numDeletedFiles = clearCacheFolder(context.getCacheDir(), numDays);
        Log.i("rlim", String.format("Cache pruning completed, %d files deleted", numDeletedFiles));
    }
}

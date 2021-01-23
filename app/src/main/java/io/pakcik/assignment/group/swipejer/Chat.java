package io.pakcik.assignment.group.swipejer;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;


public class Chat extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        WebView.setWebContentsDebuggingEnabled(true);
        webView = (WebView)findViewById(R.id.wvChat);

        WebSettings sett = webView.getSettings();
        sett.setJavaScriptEnabled(true);
        sett.setJavaScriptCanOpenWindowsAutomatically(false);

        webView.addJavascriptInterface(new ChatJsBinding(this, webView, this, PreferenceManager.getDefaultSharedPreferences(getApplicationContext())), "Chat");

        webView.clearCache(true);
        webView.loadUrl("https://swipejerchat.web.app");
        //webView.loadUrl("https://chat-swipejer.dexe.dev");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}

package io.pakcik.assignment.group.swipejer;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;


public class Chat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        WebView webView = (WebView)findViewById(R.id.wvChat);
        WebSettings sett = webView.getSettings();
        sett.setJavaScriptEnabled(true);
        webView.loadUrl("https://swipejerchat.web.app");
        sett.setJavaScriptCanOpenWindowsAutomatically(false);
        //webView.loadUrl("https://chat-swipejer.dexe.dev");
    }
}

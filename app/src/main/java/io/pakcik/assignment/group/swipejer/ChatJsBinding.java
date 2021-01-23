package io.pakcik.assignment.group.swipejer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatJsBinding {
    private Context ctx;
    private WebView wv;
    private Activity self;
    private SQLiteHelper db;
    private SharedPreferences pref;

    public ChatJsBinding(Context context, WebView webView, Activity parent, SharedPreferences pref) {
        this.ctx = context;
        this.wv = webView;
        this.self = parent;
        this.pref = pref;

        db = new SQLiteHelper(this.ctx, Config.DBName, null, 1);
    }

    @JavascriptInterface
    public String GetChatInfo() throws JSONException {
        JSONObject obj = new JSONObject();
        Intent it = self.getIntent();
        obj.put("page", it.getStringExtra("page"));
        obj.put("productID", it.getIntExtra("product_id", -1));
        obj.put("productName", it.getStringExtra("product_name"));
        obj.put("productPrice", it.getStringExtra("product_price"));
        obj.put("seller", it.getIntExtra("seller_id", -1));
        obj.put("userid", pref.getString("id", ""));
        return obj.toString();
    }

    @JavascriptInterface
    public void NavigateBack() {
        self.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (wv.canGoBack()) {
                    wv.goBack();
                } else {
                    self.finish();
                }
            }
        });
    }

    @JavascriptInterface
    public String GetItemInfo(String itemID) {
        Cursor result = db.getData("SELECT *");
        return "";
    }
}

package io.pakcik.assignment.group.swipejer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

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
    public String GetItemInfo(String itemID) throws JSONException {
        Cursor result = db.getData("SELECT * FROM PRODUCT WHERE Id = '" + itemID + "';");
        if (result.getCount() > 0) {
            result.moveToFirst();
            JSONObject resp = new JSONObject();
            resp.put("status", "success");
            resp.put("name", result.getString(2));
            resp.put("price", result.getString(3));

            byte[] foodImage = result.getBlob(6);
            Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
            String img64 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

            resp.put("img", img64);
            return resp.toString();
        } else {
            return "{ \"status\":\"notfound\" }";
        }
    }

    @JavascriptInterface
    public String GetUserInfo(String userID) throws JSONException {
        Cursor result = db.getData("SELECT * FROM users WHERE id = '" + userID + "';");
        if (result.getCount() > 0) {
            result.moveToFirst();
            JSONObject obj = new JSONObject();
            obj.put("status", "success");
            obj.put("name", result.getString(1));
            return obj.toString();
        } else {
            return "{ \"status\":\"notfound\" }";
        }
    }

    @JavascriptInterface
    public void toast(String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
}

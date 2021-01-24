package io.pakcik.assignment.group.swipejer;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SettingScreen extends AppCompatActivity implements AdapterView.OnItemClickListener  {

    ArrayList<String> listArray = new ArrayList<String>(Arrays.asList("Change Username","Change Password"
            ,"Change Location","About","Contact Us","Rate Us","Delete Account"));

    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;
    private static SQLiteHelper sqLiteHelper;

    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_screen);

        ImageButton btnProfile = (ImageButton)findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageButton btnChatBox = (ImageButton)findViewById(R.id.btnChatBox);
        btnChatBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SettingScreen.this, Chat.class);
                intent.putExtra("page", "chatroom");
                startActivity(intent);
            }
        });

        Button btnSwipeJer = (Button)findViewById(R.id.btnSwipeJer);
        btnSwipeJer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingScreen.this,SwipeActivity.class);
                startActivity(intent);
            }
        });

        final ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_settinglist,listArray);

        ListView listView = (ListView)findViewById(R.id.listSetting);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        sqLiteHelper = new SQLiteHelper(this, Config.DBName, null, 1);
        if (shp == null)
            shp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        user_id = shp.getString("id", "");



    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id){
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
        if(position==0) {
            Intent intent = new Intent(SettingScreen.this, ChangeUsername.class);
            startActivity(intent);
        }
        else if(position==1){
            Intent intent = new Intent(SettingScreen.this, ChangePassword.class);
            startActivity(intent);
        }
        else if(position==2){
            Intent intent = new Intent(SettingScreen.this, PopLocation.class);
            startActivity(intent);
        }
        else if(position==3){
            Intent intent = new Intent(SettingScreen.this, PopAbout.class);
            startActivity(intent);
        }
        else if(position==4){
            Intent intent = new Intent(SettingScreen.this, PopContactUs.class);
            startActivity(intent);
        }
        else if(position==5){
            Intent intent = new Intent(SettingScreen.this, PopRateUs.class);
            startActivity(intent);
        }
        else if(position==6){
            SettingScreen.sqLiteHelper.queryData("DELETE FROM USERS where id == " + user_id);
            SettingScreen.sqLiteHelper.queryData("DELETE FROM PRODUCT where userID == " + Integer.parseInt(user_id));

            if (shp == null)
                shp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            shpEditor = shp.edit();
            shpEditor.putString("name", "");
            shpEditor.putString("id", "");
            shpEditor.putString("email", "");
            shpEditor.putString("username", "");
            shpEditor.putString("password", "");

            shpEditor.commit();
            Intent intent = new Intent(SettingScreen.this, LoginActivity.class);
            startActivity(intent);
        }
    }

}
package io.pakcik.assignment.group.swipejer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.prefs.PreferenceChangeEvent;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextView;

    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;

    private Button LogoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextView = (TextView) findViewById(R.id.textView5);
        LogoutButton = (Button) findViewById(R.id.buttonLogout);

        shp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        CheckLogin();

        LogoutButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    if (shp == null)
                        shp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                    shpEditor = shp.edit();
                    shpEditor.putString("name", "");
                    shpEditor.putString("id", "");
                    shpEditor.putString("email", "");
                    shpEditor.putString("username", "");
                    shpEditor.putString("password", "");

                    shpEditor.commit();

                    Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();

                } catch (Exception ex) {
                    Toast.makeText(HomeActivity.this, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void CheckLogin() {
        if (shp == null)
            shp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Log.d("myTag", "in home");
        String userName = shp.getString("username", "");

        if (userName != null && !userName.equals("")) {
            mTextView.setText("Welcome " + userName);

        }else{
            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }


}
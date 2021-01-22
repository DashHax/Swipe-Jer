package io.pakcik.assignment.group.swipejer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileActivity extends AppCompatActivity {

    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;


    TextView TV_email;
    TextView TV_phoneNumber;
    TextView TV_username;
    TextView TV_location;
    TextView TV_Gender;


    Button Btn_Logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initViews();

        if (shp == null)
            shp = getSharedPreferences("myPreferences", MODE_PRIVATE);

        String user_id = shp.getString("id", "");
        String userName = shp.getString("username", "");
        String email = shp.getString("email", "");
        String pass = shp.getString("password", "");

        TV_email.setText(email.toString());
        TV_username.setText(userName.toString());


        Log.d("myTag - id", user_id);
        Log.d("myTag - email", email);
        Log.d("myTag - username", userName);
        Log.d("myTag - password", pass);

        Btn_Logout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    if (shp == null)
                        shp = getSharedPreferences("myPreferences", MODE_PRIVATE);

                    shpEditor = shp.edit();
                    shpEditor.putString("name", "");
                    shpEditor.putString("id", "");
                    shpEditor.putString("email", "");
                    shpEditor.putString("username", "");
                    shpEditor.putString("password", "");

                    shpEditor.commit();

                    Intent i = new Intent(UserProfileActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();

                } catch (Exception ex) {
                    Toast.makeText(UserProfileActivity.this, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void initViews() {
        TV_email = (TextView) findViewById(R.id.TV_Email);
        TV_phoneNumber = (TextView) findViewById(R.id.TV_phoneNumber2);
        TV_username = (TextView) findViewById(R.id.TV_username);
        TV_location = (TextView) findViewById(R.id.TV_Location2);
        TV_Gender = (TextView) findViewById(R.id.TV_Gender2);

        Btn_Logout = (Button) findViewById(R.id.Btn_Logout);


    }



}
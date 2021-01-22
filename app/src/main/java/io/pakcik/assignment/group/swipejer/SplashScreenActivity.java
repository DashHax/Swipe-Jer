package io.pakcik.assignment.group.swipejer;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;
    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        sqliteHelper = new SqliteHelper(this);

        sqliteHelper.addUser(new User(null, "aminhakim", "a@a.com", "1234qwer","" , "", "" )); //amin
        sqliteHelper.addUser(new User(null, "senoi", "senoi@topglove.com", "1234qwer","" , "", "" ));  //senoi
        sqliteHelper.addUser(new User(null, "odell", "odell@ivis.com", "1234qwer","" , "", "" ));  //odell
        sqliteHelper.addUser(new User(null, "aqiff", "aqiff@tnb.com", "1234qwer","" , "", "" ));  //aqiff
        sqliteHelper.addUser(new User(null, "fawzcopter", "fawz@fuzzyzadeh.com", "1234qwer","" , "", "" )); //fawzcopter



        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
//                Intent i = new Intent(SplashScreenActivity.this, UserProfileActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}

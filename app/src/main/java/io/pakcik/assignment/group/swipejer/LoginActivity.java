package io.pakcik.assignment.group.swipejer;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import android.content.SharedPreferences;

public class LoginActivity  extends AppCompatActivity {

    TextView signUp;

    TextView email, pass;
    Button signInButton;

    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;

    //Declaration EditTexts
    EditText editTextEmail;
    EditText editTextPassword;

    //Declaration Button
    Button buttonLogin;

    //Declaration SqliteHelper
    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
//        sqliteHelper = new SQLiteHelper(this, "" , 1 , "");
        sqLiteHelper = new SQLiteHelper(this, "SwipeJerDB.sqlite", null, 1);
        initViews();

        shp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        shpEditor = shp.edit();

        email = findViewById(R.id.TV_Email);
        pass = findViewById(R.id.TV_Password);
        signInButton = findViewById(R.id.Btn_SignIn);
//        fAuth = FirebaseAuth.getInstance();
        CheckLogin();

        //set click event of login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {

                //Check user input is correct or not
                if (validate()) {

                    //Get values from EditText fields
                    String Email = editTextEmail.getText().toString();
                    String Password = editTextPassword.getText().toString();

                    //Authenticate user
                    User currentUser = sqLiteHelper.Authenticate(new User(null, null, Email, Password, "", "", ""));


                    //Check Authentication is successful or not
                    if (currentUser != null) {
                        Log.d("myTag", currentUser.toString());
                        Log.d("myTag - id", currentUser.id);
                        Log.d("myTag - email", currentUser.email);
                        Log.d("myTag - username", currentUser.userName);
                        Log.d("myTag - password", currentUser.password);

                        Snackbar.make(buttonLogin, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();
                        if (shp == null)
                            shp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                        shpEditor.putString("id", currentUser.id);
                        shpEditor.putString("email", currentUser.email);
                        shpEditor.putString("username", currentUser.userName);
                        shpEditor.putString("password", currentUser.password);

                        shpEditor.commit();


                        Intent i = new Intent(LoginActivity.this, interestScreen.class);
                        startActivity(i);
                        finish();

                    } else {

                        //User Logged in Failed
                        Snackbar.make(buttonLogin, "Failed to log in , please try again", Snackbar.LENGTH_LONG).show();


                    }
                }
            }
        });

    }

    private void initViews() {

        editTextEmail = (EditText) findViewById(R.id.TV_Email);
        editTextPassword = (EditText) findViewById(R.id.TV_Password);
        buttonLogin = (Button) findViewById(R.id.Btn_SignIn);

    }

    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            Toast.makeText(LoginActivity.this, "Please enter valid email!", Toast.LENGTH_SHORT).show();
        } else {
            valid = true;
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            Toast.makeText(LoginActivity.this, "Please enter valid password!", Toast.LENGTH_SHORT).show();
        } else {
            if (Password.length() > 5) {
                valid = true;
            } else {
                valid = false;
                Toast.makeText(LoginActivity.this, "Password is to short!", Toast.LENGTH_SHORT).show();

            }
        }

        return valid;
    }


    public void CheckLogin() {
        if (shp == null)
            shp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String userName = shp.getString("username", "");
        Log.d("myTag", "username = " + userName);

        if (userName != null && !userName.equals("")) {
            Intent i = new Intent(LoginActivity.this, interestScreen.class);
            startActivity(i);
            finish();
        }
    }

    public void goToRegister(View view){
        Intent signup = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(signup);
    }

}

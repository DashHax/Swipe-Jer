package io.pakcik.assignment.group.swipejer;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.design.widget.TextInputLayout;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {


    TextView email, pass;
    Button signUp;

    //Declaration EditTexts
    EditText editTextUserName;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextPhoneNumber;

    //Declaration Button
    Button buttonRegister;

    //Declaration SqliteHelper
    SqliteHelper sqliteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.TB);
        setSupportActionBar(myToolbar);
        sqliteHelper = new SqliteHelper(this);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        initViews();



        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String UserName = editTextUserName.getText().toString();
                    String Email = editTextEmail.getText().toString();
                    String Password = editTextPassword.getText().toString();
                    String Phone_Number = editTextPhoneNumber.getText().toString();



                    //Check in the database is there any user associated with  this email
                    if (!sqliteHelper.isEmailExists(Email)) {

                        //Email does not exist now add new user to database
                        sqliteHelper.addUser(new User(null, UserName, Email, Password,"" , "", Phone_Number ));
//                        String id, String userName, String email, String password, String gender, String location, String phone_number
                        Snackbar.make(buttonRegister, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);
                    }else {

                        //Email exists with email input provided so show error user already exist
                        Snackbar.make(buttonRegister, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
                    }


                }
            }
        });


    }

    private void initViews() {
        editTextEmail = (EditText) findViewById(R.id.TV_Email);
        editTextPassword = (EditText) findViewById(R.id.TV_Password);
        editTextUserName = (EditText) findViewById(R.id.TV_Name);
        buttonRegister = (Button) findViewById(R.id.btn_SignUp);
        editTextPhoneNumber = (EditText) findViewById(R.id.TV_Phone);


    }


    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String UserName = editTextUserName.getText().toString();
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();
        String Phone_Number = editTextPhoneNumber.getText().toString();

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false;
            Toast.makeText(RegisterActivity.this, "Please enter valid username!", Toast.LENGTH_SHORT).show();
        } else {
            if (UserName.length() > 5) {
                valid = true;
            } else {
                valid = false;
                Toast.makeText(RegisterActivity.this, "Username is to short!", Toast.LENGTH_SHORT).show();

            }
        }

        //Handling validation for Phone number field
        if (Phone_Number.isEmpty()) {
            valid = false;
            Toast.makeText(RegisterActivity.this, "Please enter valid phone number!", Toast.LENGTH_SHORT).show();
        } else {
            if (UserName.length() > 5) {
                valid = true;
            } else {
                valid = false;
                Toast.makeText(RegisterActivity.this, "Phone number is to short!", Toast.LENGTH_SHORT).show();

            }
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            Toast.makeText(RegisterActivity.this, "Please enter valid email!", Toast.LENGTH_SHORT).show();

        } else {
            valid = true;
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            Toast.makeText(RegisterActivity.this, "Please enter valid password", Toast.LENGTH_SHORT).show();
        } else {
            if (Password.length() > 5) {
                valid = true;

            } else {
                valid = false;
                Toast.makeText(RegisterActivity.this, "Password is to short!", Toast.LENGTH_SHORT).show();
            }
        }


        return valid;
    }
}

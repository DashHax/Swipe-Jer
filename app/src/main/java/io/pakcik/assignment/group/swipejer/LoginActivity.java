package io.pakcik.assignment.group.swipejer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity  extends AppCompatActivity {

    TextView signUp;
    FirebaseAuth fAuth;

    TextView email, pass;
    Button signInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        FirebaseApp.initializeApp(this);


        email = findViewById(R.id.TV_Email);
        pass = findViewById(R.id.TV_Password);
        signInButton = findViewById(R.id.Btn_SignIn);
        fAuth = FirebaseAuth.getInstance();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signInWithEmailAndPassword("a@a.com", "1234qwer")
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Succesfully login", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), SwipeActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                }

                            };
                        });

            }
        });

    }

    public void goToRegister(View view){
        Intent signup = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(signup);
    }

}

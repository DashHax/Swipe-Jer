package io.pakcik.assignment.group.swipejer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {

    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;
    private static SQLiteHelper sqLiteHelper;

    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar tbChgPassword = (Toolbar)findViewById(R.id.tbChgPassword);
        setSupportActionBar(tbChgPassword);

        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);

        sqLiteHelper = new SQLiteHelper(this, Config.DBName, null, 1);
        if (shp == null)
            shp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        user_id = shp.getString("id", "");

        Button btnChangePass = (Button)findViewById(R.id.btnChangePass);
        btnChangePass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText etCurrentPass = (EditText)findViewById(R.id.etCurrentPass);
                EditText etNewPass = (EditText)findViewById(R.id.etNewPass);
                EditText etConfirmPass = (EditText)findViewById(R.id.etConfirmPass);

                String query;
                query = "SELECT * FROM users WHERE id = "+user_id+";";
                Cursor cursor = ChangePassword.sqLiteHelper.getData(query);
                while (cursor.moveToNext()) {
                    String oldPassword= cursor.getString(3);
                    if(etCurrentPass.getText().toString().equals(oldPassword)){
                        if(etConfirmPass.getText().toString().equals(etNewPass.getText().toString())){
                            ChangePassword.sqLiteHelper.queryData("UPDATE USERS SET password = '"+ etNewPass.getText().toString() +"' where id = " + user_id);
                            Toast.makeText(getApplicationContext(), "You have change your password!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Make sure password is same!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Enter correct password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
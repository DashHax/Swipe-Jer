package io.pakcik.assignment.group.swipejer;

import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ChangeUsername extends AppCompatActivity {

    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;
    private static SQLiteHelper sqLiteHelper;

    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);
        Toolbar tbChgUsername = (Toolbar)findViewById(R.id.tbChgUsername);
        setSupportActionBar(tbChgUsername);

        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);

        sqLiteHelper = new SQLiteHelper(this, Config.DBName, null, 1);
        if (shp == null)
            shp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        user_id = shp.getString("id", "");

        Button btnChgUsername = (Button)findViewById(R.id.btnChgUsername);
        btnChgUsername.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText etNewUsername = (EditText)findViewById(R.id.etNewUsername);

                String query;
                query = "SELECT * FROM users WHERE id = "+user_id+";";
                Cursor cursor = ChangeUsername.sqLiteHelper.getData(query);
                while (cursor.moveToNext()) {
                    ChangeUsername.sqLiteHelper.queryData("UPDATE USERS SET username = '" + etNewUsername.getText().toString() + "' where id = " + user_id);
                    Toast.makeText(getApplicationContext(), "You have change your username!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangeUsername.this, SettingScreen.class);
                    startActivity(intent);
                }
            }
        });
    }
}
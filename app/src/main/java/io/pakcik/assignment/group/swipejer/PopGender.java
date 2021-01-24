package io.pakcik.assignment.group.swipejer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class PopGender extends Activity {

    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;
    private static SQLiteHelper sqLiteHelper;

    String user_id;

    RadioGroup rgGender;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_gender);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.4));
        getWindow().setElevation(20);

        sqLiteHelper = new SQLiteHelper(this, Config.DBName, null, 1);
        if (shp == null)
            shp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        user_id = shp.getString("id", "");

        rgGender = (RadioGroup) findViewById(R.id.rgGender);

        Button btnConfirmGender = (Button) findViewById(R.id.btnConfirmGender);
        btnConfirmGender.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                int radioId = rgGender.getCheckedRadioButtonId();

                radioButton = findViewById(radioId);

                String query;
                query = "SELECT * FROM users WHERE id = "+user_id+";";
                Cursor cursor = PopGender.sqLiteHelper.getData(query);
                while (cursor.moveToNext()) {
                    PopGender.sqLiteHelper.queryData("UPDATE USERS SET gender = '" + radioButton.getText().toString() + "' where id = " + user_id);
                    Toast.makeText(getApplicationContext(), "You have change your gender!", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }
}

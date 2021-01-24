package io.pakcik.assignment.group.swipejer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PopLocation extends Activity {

    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;
    private static SQLiteHelper sqLiteHelper;

    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_location);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.3));
        getWindow().setElevation(20);

        sqLiteHelper = new SQLiteHelper(this, Config.DBName, null, 1);
        if (shp == null)
            shp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        user_id = shp.getString("id", "");

        Button btnNewLocation = (Button)findViewById(R.id.btnNewLocation);
        btnNewLocation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText etNewLocation = (EditText)findViewById(R.id.etNewLocation);

                String query;
                query = "SELECT * FROM users WHERE id = "+user_id+";";
                Cursor cursor = PopLocation.sqLiteHelper.getData(query);
                while (cursor.moveToNext()) {
                    PopLocation.sqLiteHelper.queryData("UPDATE USERS SET location = '" + etNewLocation.getText().toString() + "' where id = " + user_id);
                    Toast.makeText(getApplicationContext(), "You have change your location!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}

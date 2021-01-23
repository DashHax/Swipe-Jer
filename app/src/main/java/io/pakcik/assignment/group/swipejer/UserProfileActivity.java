package io.pakcik.assignment.group.swipejer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

    ImageView item1;
    ImageView item2;
    ImageView item3;
    ImageView item4;

    private static SQLiteHelper sqLiteHelper;


    Button Btn_Logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initViews();
        sqLiteHelper = new SQLiteHelper(this, Config.DBName, null, 1);
        if (shp == null)
            shp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String user_id = shp.getString("id", "");
        String userName = shp.getString("username", "");
        String email = shp.getString("email", "");
        String pass = shp.getString("password", "");


        TV_email.setText(email.toString());
        TV_username.setText(userName.toString());

        //To go back to previous activity when profile is clicked
        ImageView back = (ImageView)findViewById(R.id.imageView4);
        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Log.d("myTag - id", user_id);
        Log.d("myTag - email", email);
        Log.d("myTag - username", userName);
        Log.d("myTag - password", pass);

        Cursor cursor = UserProfileActivity.sqLiteHelper.getData("SELECT * FROM users");
        Log.d("User ID: " ,  cursor.toString());

        while (cursor.moveToNext()) {

            if (cursor.getInt(0) == Integer.parseInt(user_id)){

                Log.d("User ID: " ,  String.valueOf(cursor.getInt(0)));
                Log.d("User username: " ,  String.valueOf(cursor.getString(1)));
                Log.d("User email: " ,  String.valueOf(cursor.getString(2)));
                Log.d("User password: " ,  String.valueOf(cursor.getString(3)));
                Log.d("User gender: " ,  String.valueOf(cursor.getString(4)));
                Log.d("User phone number: " ,  String.valueOf(cursor.getString(5)));
                Log.d("User location: " ,  String.valueOf(cursor.getString(6)));


                TV_Gender.setText(String.valueOf(cursor.getString(4)));
                TV_phoneNumber.setText(String.valueOf(cursor.getString(5)));
                TV_location.setText(String.valueOf(cursor.getString(6)));

            }

        }

        Cursor cursorImage = UserProfileActivity.sqLiteHelper.getData("SELECT * FROM PRODUCT where PRODUCT.userID == " + user_id);
        Log.d("User ID: " ,  cursor.toString());
        Integer count = 1;

        while (cursorImage.moveToNext()) {
            if (count == 1){
                byte[] image = cursorImage.getBlob(6);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                item1.setVisibility(View.VISIBLE);
                item1.setImageBitmap(bitmap);
                Log.d("Image 1", "Menjadi");
            }

            if (count == 2){
                byte[] image = cursorImage.getBlob(6);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                item2.setVisibility(View.VISIBLE);
                item2.setImageBitmap(bitmap);
                Log.d("Image 2", "Menjadi");
            }

            if (count == 3){
                byte[] image = cursorImage.getBlob(6);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                item3.setVisibility(View.VISIBLE);
                item3.setImageBitmap(bitmap);
                Log.d("Image 3", "Menjadi");
            }
            if (count == 4){
                byte[] image = cursorImage.getBlob(6);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                item4.setVisibility(View.VISIBLE);
                item4.setImageBitmap(bitmap);
                Log.d("Image 4", "Menjadi");
            }

            count++;
        }

    }

    private void initViews() {
        TV_email = (TextView) findViewById(R.id.TV_Email);
        TV_phoneNumber = (TextView) findViewById(R.id.TV_phoneNumber2);
        TV_username = (TextView) findViewById(R.id.TV_username);
        TV_location = (TextView) findViewById(R.id.TV_Location2);
        TV_Gender = (TextView) findViewById(R.id.TV_Gender2);
        item1 = (ImageView) findViewById(R.id.item_1);
        item2 = (ImageView) findViewById(R.id.item_2);
        item3 = (ImageView) findViewById(R.id.item_3);
        item4 = (ImageView) findViewById(R.id.item_4);

        item1.setVisibility(View.INVISIBLE);
        item2.setVisibility(View.INVISIBLE);
        item3.setVisibility(View.INVISIBLE);
        item4.setVisibility(View.INVISIBLE);
    }




}
package io.pakcik.assignment.group.swipejer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import androidx.appcompat.app.AppCompatActivity;

public class interestScreen extends AppCompatActivity {
    private static SQLiteHelper sqLiteHelper;

    SharedPreferences shp;
    private PopupMenu mPopupMenu;
    SharedPreferences.Editor shpEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interestscreen);

        InitDatabase();

        ImageButton profileBtn = (ImageButton) findViewById(R.id.btnProfile);
        ImageButton toListing = (ImageButton)findViewById(R.id.imageButton2);
        ImageButton chatboxBtn = (ImageButton) findViewById(R.id.btnChatbox);

        mPopupMenu = new PopupMenu(this, profileBtn);
        MenuInflater menuInflater = mPopupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, mPopupMenu.getMenu());
        if (shp == null)
            shp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        chatboxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(interestScreen.this, Chat.class);
                intent.putExtra("page", "chatroom");
                startActivity(intent);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupMenu.show();
                mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Profile")) {
                            Intent profile = new Intent(interestScreen.this, UserProfileActivity.class);
                            startActivity(profile);
                        }


                        else if (item.getTitle().equals("Logout")){
                            try {
                                if (shp == null)
                                    shp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                                shpEditor = shp.edit();
                                shpEditor.putString("name", "");
                                shpEditor.putString("id", "");
                                shpEditor.putString("email", "");
                                shpEditor.putString("username", "");
                                shpEditor.putString("password", "");

                                shpEditor.commit();

                                Intent i = new Intent(interestScreen.this, LoginActivity.class);
                                startActivity(i);
                                finish();

                            } catch (Exception ex) {
                                Toast.makeText(interestScreen.this, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        }





                        return true;
                    }
                });
            }
        });


        toListing.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(interestScreen.this,addListing.class);
                startActivity(intent);
            }
        });


        Button swipejer = (Button)findViewById(R.id.btnSwipeJer);
        swipejer.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(interestScreen.this,SwipeActivity.class);
                startActivity(intent);
            }
        });

        final Button menfashionCategory = (Button)findViewById(R.id.menfashion);
        Button womenfashionCategory = (Button)findViewById(R.id.womenfashion);
        Button electronics = (Button)findViewById(R.id.electronics);
        Button mobileGadgets = (Button)findViewById(R.id.gadgets);
        ImageButton search = (ImageButton)findViewById(R.id.searchItem);

        final EditText searchItem = (EditText)findViewById(R.id.editText);

        search.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (!(searchItem.getText().equals(""))){
                    String namaItem = searchItem.getText().toString();
                    Intent intent = new Intent(interestScreen.this,SwipeActivity.class);
                    intent.putExtra("name", namaItem);
                    startActivity(intent);
            }
        }
        });




        menfashionCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(interestScreen.this,SwipeActivity.class);
                String category = "Men Fashion";
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        womenfashionCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(interestScreen.this,SwipeActivity.class);
                String category = "Women Fashion";
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        electronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(interestScreen.this,SwipeActivity.class);
                String category = "Electronics";
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        mobileGadgets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(interestScreen.this,SwipeActivity.class);
                String category = "Gadgets";
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });


    }

    private void InitDatabase() {

        try {
            sqLiteHelper = new SQLiteHelper(this, Config.DBName, null, 1);

            sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS PRODUCT(Id INTEGER PRIMARY KEY AUTOINCREMENT, userID INT, name VARCHAR, price VARCHAR, description TEXT, category VARCHAR, image BLOB)");

            /*Insert data*/
            Cursor result = sqLiteHelper.getData("SELECT * FROM product WHERE (name LIKE '%Phone%');");
            if (!result.moveToNext()) {
                sqLiteHelper.insertData(1, "iPhone 12 Pro", "4999.00","used iPhone, want to buy new","Gadgets",drawableToByte(R.drawable.test));
                sqLiteHelper.insertData(2, "iPhone 12 Pro Max", "6999.00","test","Gadgets",drawableToByte(R.drawable.ip12));
                sqLiteHelper.insertData(1, "iPhone 11", "3999.00","used iPhone, want to buy new","Gadgets",drawableToByte(R.drawable.iphone));
                sqLiteHelper.insertData(2, "Xiaomi Redmi K30", "1599.00","used for 1 year, condition like neelofa","Women Fashion",drawableToByte(R.drawable.xiaomiredmi30));
                sqLiteHelper.insertData(3, "Lenovo Laptop", "6599.00","used for 1 year, condition like neelofa","Men Fashion",drawableToByte(R.drawable.laptop));
            } else {
                Log.d("myTag", "Data already exist!");
            }

            sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS chatroom (id INTEGER PRIMARY KEY AUTOINCREMENT, user_1 INT, user_2 INT, product_id INT);");
            sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS attachments (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, att_type TEXT, content BLOB);");
            sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS meetings (id INTEGER PRIMARY KEY AUTOINCREMENT, place_name TEXT, place_desc TEXT, datetime TEXT, finished INTEGER);");
            sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS chat_entry (chatroom_id INTEGER, from_id INTEGER, to_id INTEGER, message TEXT, time TEXT, attachment_id INTEGER, meeting_id INTEGER);");
        } catch (Exception ex) {
            Log.d("DB", "Error while init db: " + ex.getMessage());
        }
    }

    public byte[] drawableToByte(int image) {
        Resources res = getResources();
        Drawable drawable = res.getDrawable(image);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapData = stream.toByteArray();
        return bitMapData;
    }

}



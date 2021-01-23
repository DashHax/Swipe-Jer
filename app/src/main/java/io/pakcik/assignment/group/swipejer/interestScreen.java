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
                        else if (item.getTitle().equals("Settings")) {
                            Intent settings = new Intent(interestScreen.this, SettingScreen.class);
                            startActivity(settings);
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
            sqLiteHelper.queryData("DROP TABLE IF EXISTS PRODUCT");
            sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS PRODUCT(Id INTEGER PRIMARY KEY AUTOINCREMENT, userID INT, name VARCHAR, price VARCHAR, description TEXT, category VARCHAR, image BLOB)");


            /*Insert data*/
            Cursor result = sqLiteHelper.getData("SELECT * FROM product WHERE (name LIKE '%Phone%');");
            if (!result.moveToNext()) {

                //Gadgets category
                sqLiteHelper.insertData(1, "iPhone 12 Pro", "4999.00","need urgent money, still have warranty","Gadgets",drawableToByte(R.drawable.test));
                sqLiteHelper.insertData(2, "iPhone 12 Pro Max", "6999.00","The phone is too big for me ... I have small hands","Gadgets",drawableToByte(R.drawable.ip12));
                sqLiteHelper.insertData(3, "iPhone 11", "3999.00","used iPhone, not less than 1 year. Want to buy new","Gadgets",drawableToByte(R.drawable.iphone));
                sqLiteHelper.insertData(4, "Xiaomi Redmi K30", "1899.00","used for 1 year, condition like neelofa","Gadgets",drawableToByte(R.drawable.xiaomiredmi30));
                sqLiteHelper.insertData(5, "Lenovo Legion Y Laptop", "7599.00","Need to buy new laptop, dont really like the laptop","Gadgets",drawableToByte(R.drawable.laptop));
                sqLiteHelper.insertData(2, "Oppo Earphone", "68.00","New earphone, 5mm earphone earjack","Gadgets",drawableToByte(R.drawable.oppoearphone));
                sqLiteHelper.insertData(3, "Apple Watch Series 4 GPS", "1299.00","Used for 5 months","Gadgets",drawableToByte(R.drawable.applewatch4));
                sqLiteHelper.insertData(4, "Zeblaze Thor 5 Pro", "899.00","The watch is too big for me, can COD and check face to face","Gadgets",drawableToByte(R.drawable.zeblaze));
                sqLiteHelper.insertData(1, "Surface Laptop", "4399.00","used Surface laptop","Gadgets",drawableToByte(R.drawable.surfacelaptop));
                sqLiteHelper.insertData(2, "Suunto 7", "6599.00","used for 1 year, condition like neelofa","Gadgets",drawableToByte(R.drawable.suunto7));

                //Electronics category
                sqLiteHelper.insertData(1, "Mistral Rice Cooker", "399.00","used rice cooker, want to buy new one","Electronics",drawableToByte(R.drawable.mistral));
                sqLiteHelper.insertData(2, "Russell Hobbs Rice Cooker", "899.00","High end rice cooker","Electronics",drawableToByte(R.drawable.rusellhobbs));
                sqLiteHelper.insertData(3, "Pensonic Rice Cooker", "599.00","used the rice cooker less than a week","Electronics",drawableToByte(R.drawable.pensonic));
                sqLiteHelper.insertData(4, "Oceanic TV", "499.00","used television, 31 inch","Electronics",drawableToByte(R.drawable.oceanic));
                sqLiteHelper.insertData(5, "Daewoo 2 in 1 Vacuum Cleaner", "1099.00","1400W, 2 in 1 Vacuum Cleaner with Blower","Electronics",drawableToByte(R.drawable.daewoo));
                sqLiteHelper.insertData(1, "KHIND Vacuum Cleaner", "699.00","Very good and powerful vacuum. Secondhand","Electronics",drawableToByte(R.drawable.khind));
                sqLiteHelper.insertData(2, "Morgan Vacuum Cleaner", "829.00","Light and powerful vacuum","Electronics",drawableToByte(R.drawable.morgan));
                sqLiteHelper.insertData(3, "Philips Rice Cooker", "769.00","New Rice cooker","Electronics",drawableToByte(R.drawable.philips2));
                sqLiteHelper.insertData(4, "Sharp Rice Cooker", "109.00","used rice cooker","Electronics",drawableToByte(R.drawable.sharp));
                sqLiteHelper.insertData(5, "Khind Rice Cooker", "1599.00","used for 1 year, condition like neelofa","Electronics",drawableToByte(R.drawable.khindrc));
                sqLiteHelper.insertData(1, "Pensonic Small and Cute Rice Cooker", "89.00","small rice cooker, enough for 1 and 2 person","Electronics",drawableToByte(R.drawable.pensonicsmall));

                //Men Fashion
                sqLiteHelper.insertData(2, "BVGARI Wallet", "2399.00","100% Original Wallet from Paris","Men Fashion",drawableToByte(R.drawable.bvgari));
                sqLiteHelper.insertData(3, "X.D Bolo", "199.00","Used less than 1 week. Its waterproof. COD available","Men Fashion",drawableToByte(R.drawable.xdbolo));
                sqLiteHelper.insertData(4, "Brown Shirt LLBear", "49.00","Preloved brown shirt (long sleeve)","Men Fashion",drawableToByte(R.drawable.llbear));
                sqLiteHelper.insertData(5, "Checker Formal Trousers Dark Blue", "109.00","For men. Material:Linen 100% Comfortable","Men Fashion",drawableToByte(R.drawable.linen));
                sqLiteHelper.insertData(1, "Converse High Cut", "149.00","Preloved converse high cut shoe. Size UK 8","Men Fashion",drawableToByte(R.drawable.converse));
                sqLiteHelper.insertData(1, "Sport Fashion Black Shoe", "69.00","Black Sport Fashion Shoe. Size UK 6.5","Men Fashion",drawableToByte(R.drawable.sportfashion));
                sqLiteHelper.insertData(2, "Jumia Loafers", "75.00","Black Loafers. Used. Size 9UK. PM for COD or delivery.","Men Fashion",drawableToByte(R.drawable.lowcut));
                sqLiteHelper.insertData(2, "Lacoste Wallet with card holder", "219.00","Brand new lacoste with card holder. COD is preferable","Men Fashion",drawableToByte(R.drawable.lacoste));
                sqLiteHelper.insertData(3, "Floral Shirt", "1599.00","Floral Shirt. Size M","Men Fashion",drawableToByte(R.drawable.floral));
                sqLiteHelper.insertData(3, "Nike Jogging ", "123.00","Size 7 UK. Used for 1 year. Not fit anymore","Men Fashion",drawableToByte(R.drawable.nikewarna));
                sqLiteHelper.insertData(4, "Formal Trousers for Men", "43.00","White Formal Trouses. Waist 27 inch","Men Fashion",drawableToByte(R.drawable.whitetrousers));
                sqLiteHelper.insertData(4, "Clarks Loafers", "163.00","Dark Blue loafers by Clarks. 100% Original and used","Men Fashion",drawableToByte(R.drawable.clarks));
                sqLiteHelper.insertData(5, "Jogging Pants", "59.00","Jogging Pants for men. Silver. Size M, length 100cm","Men Fashion",drawableToByte(R.drawable.jogging));
                sqLiteHelper.insertData(5, "Checker Formal Trousers Grey", "109.00","For men. Material:Linen 100% Comfortable","Men Fashion",drawableToByte(R.drawable.checkersgrey));
                sqLiteHelper.insertData(1, "Lazy Pants for Men", "119.00","For men. Material:Linen 100% Comfortable and very loose","Men Fashion",drawableToByte(R.drawable.lazypants));

                //Women Fashion
                sqLiteHelper.insertData(1, "Forever Young Long Purse", "209.00","used long purse, 100% original, from Korea. Annyeonghaseyo","Women Fashion",drawableToByte(R.drawable.fy));
                sqLiteHelper.insertData(2, "Coach Purse for Women", "1999.00","Brand New Coach Purse, 100% Original. From Paris","Women Fashion",drawableToByte(R.drawable.coachpurse));
                sqLiteHelper.insertData(3, "Levi's White Tshirt", "199.00","used Levi's tshirt for women","Women Fashion",drawableToByte(R.drawable.leviswomen));
                sqLiteHelper.insertData(4, "H&M Slack", "59.00","White slack for women, waist 28''","Women Fashion",drawableToByte(R.drawable.hmslack));
                sqLiteHelper.insertData(5, "Shirt Polka Dot", "89.00","Shirt can wear for formal. Size M","Women Fashion",drawableToByte(R.drawable.polkadot));
                sqLiteHelper.insertData(2, "Uniqlo Brown Tshirt", "39.00","Preloved Uniqlo Brown Tshirt, size S. Can COD, RM10","Women Fashion",drawableToByte(R.drawable.uniqlo));
                sqLiteHelper.insertData(1, "Oxhide Long Purse", "199.00","Oxhide Long Purse, used for 1 month","Women Fashion",drawableToByte(R.drawable.oxhide));
                sqLiteHelper.insertData(3, "Denim Shirt for Women", "71.00","Denim Shirt for women, size M, did not really like the fabric","Women Fashion",drawableToByte(R.drawable.denimwomen));
                sqLiteHelper.insertData(4, "High heels Gucci", "3099.00","used heels, receipt from London as proof. Feet is UK 6","Women Fashion",drawableToByte(R.drawable.highheels));
                sqLiteHelper.insertData(1, "Palazzo Dark Green", "92.00","New stock dark green palazzo","Women Fashion",drawableToByte(R.drawable.palazoo));

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



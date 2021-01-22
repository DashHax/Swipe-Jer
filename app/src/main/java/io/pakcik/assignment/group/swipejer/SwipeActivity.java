package io.pakcik.assignment.group.swipejer;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class SwipeActivity extends AppCompatActivity {

    private cards cards_date[];
    private ArrayAdapter arrayAdapter;
    private int i;
    ListView listView;
    List<cards> rowItems;
    Button reverse;


    private String currentUId;
    public static SQLiteHelper sqLiteHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipescreen_layout);

        rowItems = new ArrayList<cards>();

        sqLiteHelper = new SQLiteHelper(this, "SwipeJerDB.sqlite", null, 1);

        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS PRODUCT(Id INTEGER PRIMARY KEY AUTOINCREMENT, userID INT, name VARCHAR, price VARCHAR, description TEXT, category VARCHAR, image BLOB)");
        sqLiteHelper.insertData(1, "iPhone 11", "3999.00","used iPhone, want to buy new","Gadgets",drawableToByte(R.drawable.test));
        sqLiteHelper.insertData(1, "iPhone 10 Max", "4999.00","test","Gadgets",drawableToByte(R.drawable.test));
//        sqLiteHelper.insertData(2, "Samsung Uchiha", "500.00","test",drawableToByte(R.drawable.test2));
//        sqLiteHelper.insertData(3, "Nokia Karasuno", "439.00","test",drawableToByte(R.drawable.test3));
//        sqLiteHelper.insertData(4, "BlackBebi", "2030.00","test",drawableToByte(R.drawable.test4));

        arrayAdapter = new arrayAdapter(this, R.layout.item, rowItems );

        // Select query
        // Need to add logic for where clause
        String query = "SELECT * FROM product";

        // get all data from sqlite
        Cursor cursor = SwipeActivity.sqLiteHelper.getData(query);
        rowItems.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int user_id = cursor.getInt(1);
            String name = cursor.getString(2);
            String price = cursor.getString(3);
            String description = cursor.getString(5);
            byte[] image = cursor.getBlob(6);

            rowItems.add(new cards(user_id, name, price,description,image));
        }
        arrayAdapter.notifyDataSetChanged();


        try {
            show_name();
        } catch (Exception e) {
            e.printStackTrace();
        };

        //add the view via xml or programmatically
        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);


        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                cards temp = rowItems.get(0);
                rowItems.remove(0);
                rowItems.add(temp);
                try {
                    show_name();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
//                Toast.makeText(MainActivity.this, "Left!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Intent intent = new Intent(getApplicationContext(), Chat.class);
                startActivity(intent);
//                Toast.makeText(MainActivity.this, "Right!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
//                Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
//                rowItems.remove(0);
//                arrayAdapter.notifyDataSetChanged();
                String Prod_name = rowItems.get(0).getName();
                String Prod_price = rowItems.get(0).getPrice();
                String Prod_desc = rowItems.get(0).getDescription();

                Intent intent = new Intent(getApplicationContext(), PopUpWindow.class);
                intent.putExtra("product_name", Prod_name);
                intent.putExtra("product_price", Prod_price);
                intent.putExtra("product_desc", Prod_desc);

                startActivity(intent);

            }
        });

        ImageButton back = (ImageButton) findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Back is clicked", Toast.LENGTH_SHORT).show();
                SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
                flingContainer.setAdapter(arrayAdapter);
                int final_index = rowItems.size()-1;
                Collections.swap(rowItems, 1, final_index);
                flingContainer.getTopCardListener().selectLeft();
                arrayAdapter.notifyDataSetChanged();
//                Collections.swap(rowItems, 0, -1);
            }
        });

        ImageButton dislike = (ImageButton) findViewById(R.id.btnCanel);
        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Dislike is clicked", Toast.LENGTH_SHORT).show();
                SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
                flingContainer.setAdapter(arrayAdapter);
                flingContainer.getTopCardListener().selectLeft();
                arrayAdapter.notifyDataSetChanged();
//                Collections.swap(rowItems, 0, -1);
            }
        });

        ImageButton like = (ImageButton) findViewById(R.id.btnHear);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Chat.class);
                startActivity(intent);
            }
        });

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

    public static byte[] imageViewToByte(ImageView  image) {
        Drawable myDrawable = image.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)myDrawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void show_name() throws Exception {
        try {
            TextView name = (TextView) this.findViewById(R.id.textView2);
            TextView price = (TextView) this.findViewById(R.id.textView3);
            name.setText(rowItems.get(0).getName());
            price.setText("RM "+rowItems.get(0).getPrice());
        }
        catch (Exception e){
            TextView name = (TextView) this.findViewById(R.id.textView2);
            TextView price = (TextView) this.findViewById(R.id.textView3);
            name.setText("None");
            price.setText("None");
            try {
                throw new Exception("Error in Routine 2", e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}

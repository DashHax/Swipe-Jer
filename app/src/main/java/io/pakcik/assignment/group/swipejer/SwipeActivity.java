package io.pakcik.assignment.group.swipejer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import com.lorentzos.flingswipe.SwipeFlingAdapterView;

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

    private String query;
    private String currentUId;
    private static SQLiteHelper sqLiteHelper;

    SharedPreferences shp;
    private PopupMenu mPopupMenu;
    SharedPreferences.Editor shpEditor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipescreen_layout);

        ImageButton btnChat = (ImageButton)findViewById(R.id.btnGoToChatbox);

        rowItems = new ArrayList<cards>();

        sqLiteHelper = new SQLiteHelper(this, Config.DBName, null, 1);

        arrayAdapter = new arrayAdapter(this, R.layout.item, rowItems );
        if (shp == null)
            shp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String current_user = shp.getString("id", "");

        Intent _int = getIntent();
        if (_int.hasExtra("category")) {
            query = "SELECT * FROM product WHERE category = '" + _int.getStringExtra("category") + "' AND userID != "+current_user +";";
        } else if (_int.hasExtra("name")) {
            query = "SELECT * FROM product WHERE (name LIKE '%" + _int.getStringExtra("name") + "%') AND userID != "+current_user +";";
        } else {
            query = "SELECT * FROM product WHERE userID != "+current_user+";";
        }

        // Select query
        /*try {
            String category = getIntent().getStringExtra("category");
            Log.d("test",category);

            query = "SELECT * FROM product WHERE category = '" + category+"'";
            Log.d("test",query);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test","No category");
            query = "SELECT * FROM product";
        }

        try {
            String name = getIntent().getStringExtra("name");
            Log.d("test",name);

            query = "SELECT * FROM PRODUCT WHERE (name LIKE '%"+ name+ "%')";
            Log.d("test",query);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test","No category");
            query = "SELECT * FROM product";
        }*/

        // Need to add logic for where clause

        Log.d("items", "query = " + query);
        // get all data from sqlite
        Cursor cursor = SwipeActivity.sqLiteHelper.getData(query);
        rowItems.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int user_id = cursor.getInt(1);
            String name = cursor.getString(2);
            String price = cursor.getString(3);
            String description = cursor.getString(4);
            byte[] image = cursor.getBlob(6);

            rowItems.add(new cards(id, user_id, name, price,description,image));
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
                String p_name = rowItems.get(0).getName();
                int p_id = rowItems.get(0).getItemId();
                String p_price = rowItems.get(0).getPrice();
                int p_userid = rowItems.get(0).getUserId();
                intent.putExtra("page", "chatbox");
                intent.putExtra("product_id", p_id);
                intent.putExtra("product_name", p_name);
                intent.putExtra("product_price", p_price);
                intent.putExtra("seller_id", p_userid);
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
                String p_name = rowItems.get(0).getName();
                int p_id = rowItems.get(0).getItemId();
                String p_price = rowItems.get(0).getPrice();
                int p_userid = rowItems.get(0).getUserId();

                Intent intent = new Intent(getApplicationContext(), Chat.class);
                intent.putExtra("page", "chatbox");
                intent.putExtra("product_id", p_id);
                intent.putExtra("product_name", p_name);
                intent.putExtra("product_price", p_price);
                intent.putExtra("seller_id", p_userid);
                startActivity(intent);
            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SwipeActivity.this, Chat.class);
                intent.putExtra("page", "chatroom");
                startActivity(intent);
            }
        });

        // Click Swipejer button
        Button swipejerbtn = (Button) findViewById(R.id.btnSwipeJer);
        swipejerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), interestScreen.class);
                startActivity(intent);
            }
        });
        ImageButton profileBtn = (ImageButton) findViewById(R.id.btnProfile);
        // Popup menu
        mPopupMenu = new PopupMenu(this, profileBtn);
        MenuInflater menuInflater = mPopupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, mPopupMenu.getMenu());
        if (shp == null)
            shp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupMenu.show();
                mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Profile")) {
                            Intent profile = new Intent(SwipeActivity.this, UserProfileActivity.class);
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

                                Intent i = new Intent(SwipeActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();

                            } catch (Exception ex) {
                                Toast.makeText(SwipeActivity.this, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        }





                        return true;
                    }
                });
            }
        });

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

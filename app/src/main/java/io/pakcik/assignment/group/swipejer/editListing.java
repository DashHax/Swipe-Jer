package io.pakcik.assignment.group.swipejer;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class editListing  extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText itemName, itemPrice, itemDesc;
    String category="";
    Button updateBtn, deleteBtn;
    ImageView itemView1;
    final int REQUEST_CODE_GALLERY = 999;
    SharedPreferences shp;
    public static SQLiteHelper sqLiteHelper;
    private String query;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editlisting);
        Intent _int = getIntent();
        String item_ID  = _int.getStringExtra("id");
        final int itemID = Integer.parseInt(item_ID);


        final Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        init();




        sqLiteHelper = new SQLiteHelper(this, Config.DBName, null, 1);
//        sqLiteHelper.queryData("DROP TABLE IF EXISTS PRODUCT");

        itemView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        editListing.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        if (shp == null)
            shp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String user_id = shp.getString("id", "");
        final int userid = Integer.parseInt(user_id);

        query = "SELECT * FROM product WHERE id = "+item_ID+";";


        Cursor cursor = editListing.sqLiteHelper.getData(query);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(2);
            String price = cursor.getString(3);
            String description = cursor.getString(4);
            String category = cursor.getString(5);
            byte[] image = cursor.getBlob(6);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            itemName.setText(name.toString());
            itemPrice.setText(price.toString());
            if (category.equals("Men Fashion")){
                int pos =0;
                spinner.setSelection(pos);
            }
            else if (category.equals("Women Fashion")){
                int pos =1;
                spinner.setSelection(pos);
            }
            else if (category.equals("Electronics")){
                int pos =2;
                spinner.setSelection(pos);
            }
            else{
                int pos=3;
                spinner.setSelection(pos);
            }
            itemDesc.setText(description.toString());
            itemView1.setImageBitmap(bitmap);

        }


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Log.d("",itemName.getText().toString());
                    sqLiteHelper.updateData(
                            userid,
                            itemName.getText().toString().trim(),
                            itemPrice.getText().toString().trim(),
                            itemDesc.getText().toString().trim(),
                            category,
                            imageViewToByte(itemView1),
                            itemID
                    );
                    Toast.makeText(getApplicationContext(), "Updated successfully!", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        Button swipejer = (Button)findViewById(R.id.btnSwipeJer);
        swipejer.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(editListing.this,SwipeActivity.class);
                startActivity(intent);
            }
        });


        ImageView back = (ImageView)findViewById(R.id.imageView4);
        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
            try{
                sqLiteHelper.deleteData(itemID);
                Toast.makeText(getApplicationContext(), "Deleted successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(editListing.this,UserProfileActivity.class);
                startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }
            }
        });
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUEST_CODE_GALLERY){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                itemView1.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init(){
        itemName = (EditText)findViewById(R.id.item);
        itemPrice = (EditText)findViewById(R.id.price);
        updateBtn = (Button)findViewById(R.id.updateBtn);
        deleteBtn = (Button)findViewById(R.id.deleteBtn);
        itemDesc = (EditText)findViewById(R.id.itemDescrip);
        itemView1 = (ImageView)findViewById(R.id.imageView1);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),category, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

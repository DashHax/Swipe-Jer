package io.pakcik.assignment.group.swipejer;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    int userID;
    String category="";
    Button updateBtn, deleteBtn;
    ImageView itemView1;
    final int REQUEST_CODE_GALLERY = 999;
    SharedPreferences shp;
    public static SQLiteHelper sqLiteHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editlisting);


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
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    sqLiteHelper.updateData(
                            userid,
                            itemName.getText().toString().trim(),
                            itemPrice.getText().toString().trim(),
                            itemDesc.getText().toString().trim(),
                            category,
                            imageViewToByte(itemView1),
                            1
                    );
                    Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                    itemName.setText("");
                    itemPrice.setText("");
                    itemDesc.setText("");
                    itemView1.setImageResource(R.drawable.additem);
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

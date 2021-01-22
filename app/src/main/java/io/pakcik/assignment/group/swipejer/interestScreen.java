package io.pakcik.assignment.group.swipejer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

public class interestScreen extends AppCompatActivity {
    private PopupMenu mPopupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interestscreen);
        ImageButton imageButton = (ImageButton) findViewById(R.id.btnProfile);
        ImageButton addButton = (ImageButton)findViewById(R.id.imageButton2);
        mPopupMenu = new PopupMenu(this, imageButton);
        MenuInflater menuInflater = mPopupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, mPopupMenu.getMenu());
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupMenu.show();
            }
        });
    }

    public void addItemPage(View view){
        Intent intent = new Intent(interestScreen.this,addListing.class);
        startActivity(intent);
    }

}

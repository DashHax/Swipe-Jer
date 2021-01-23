package io.pakcik.assignment.group.swipejer;

import androidx.appcompat.app.ActionBar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ChangeUsername extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);
        Toolbar tbChgUsername = (Toolbar)findViewById(R.id.tbChgUsername);
        setSupportActionBar(tbChgUsername);

        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
    }
}
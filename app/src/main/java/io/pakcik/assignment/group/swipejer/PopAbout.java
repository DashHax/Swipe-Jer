package io.pakcik.assignment.group.swipejer;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class PopAbout extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_about);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.5));
        getWindow().setElevation(20);
    }
}

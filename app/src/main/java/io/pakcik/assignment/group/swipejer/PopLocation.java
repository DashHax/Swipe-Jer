package io.pakcik.assignment.group.swipejer;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class PopLocation extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_location);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.3));
        getWindow().setElevation(20);
    }
}

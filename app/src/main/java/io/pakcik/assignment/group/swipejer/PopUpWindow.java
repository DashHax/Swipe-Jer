package io.pakcik.assignment.group.swipejer;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;

public class PopUpWindow extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);
        DisplayMetrics dm =  new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width), (int)(height *.8));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x=0;
        params.y=170;

        getWindow().setAttributes(params);

        Log.d("LIST", String.valueOf(height));

        String prod_name = getIntent().getStringExtra("product_name");
        String prod_price = getIntent().getStringExtra("product_price");
        String prod_desc = getIntent().getStringExtra("product_desc");

        TextView name = (TextView) this.findViewById(R.id.TVProd_Title);
        TextView price = (TextView) this.findViewById(R.id.TVProd_Price);
        TextView desc = (TextView) this.findViewById(R.id.TVProd_desc);

        name.setText(prod_name);
        price.setText("RM "+prod_price);
        desc.setText(prod_desc);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

//        ImageView imageView = (ImageView) findViewById(R.id.imageView);
//        imageView.setImageResource(R.drawable.hztr);

    }
}
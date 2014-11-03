package com.unique.singlecycle.l_weight;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ui.RectangleButton;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RectangleButton rectangleButton = (RectangleButton) findViewById(R.id.rectangle_button);
        rectangleButton.setText("SingleCycle");
        rectangleButton.setTextColor(0xffffffff);
        rectangleButton.setRippleSpeed(20f);
        rectangleButton.setRippleSize(40);
        rectangleButton.setRippleColor(0xff000000);
    }


}

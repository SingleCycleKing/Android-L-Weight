package com.unique.singlecycle.l_weight;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import ui.RectangleButton;
import ui.RippleLayout;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RectangleButton rectangleButton = (RectangleButton) findViewById(R.id.rectangle_button);
        rectangleButton.setText("SingleCycle");
        rectangleButton.setTextColor(0xffffffff);
        rectangleButton.setRippleSpeed(20f);
        rectangleButton.setRippleSize(5);
        rectangleButton.setRippleColor(0xff000000);


    }
}

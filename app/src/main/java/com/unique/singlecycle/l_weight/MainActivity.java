package com.unique.singlecycle.l_weight;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import ui.FloatButton;
import ui.RectangleButton;
import ui.RippleLayout;
import utils.DeBugLog;


public class MainActivity extends Activity {
    private boolean isBottom = false;


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


        final FloatButton floatButton = (FloatButton) findViewById(R.id.float_button);
        Button button = (Button) findViewById(R.id.click);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isBottom) {
                    floatButton.floatButtonIn();
                    isBottom = true;
                } else {
                    floatButton.floatButtonOut();
                    isBottom = false;
                }
            }
        });


    }
}

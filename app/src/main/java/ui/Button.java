package ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by singlecycle on 10/30/14.
 */
public abstract class Button extends RelativeLayout {
    private int minWidth;
    private int minHeight;
    private int background;
    private int backgroundColor = Color.parseColor("#1E88E5");
    private int disabledBackgroundColor = Color.parseColor("#E2E2E2");
    private boolean isLastTouch = false;

    public Button(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDefaultProperties();
        setAttributes(attrs);
    }

    //set default
    protected void setDefaultProperties() {
        setMinimumHeight(minHeight);
        setMinimumWidth(minWidth);
        setBackgroundResource(background);
        setBackgroundColor(backgroundColor);

    }

    //set attributes from xml
    abstract protected void setAttributes(AttributeSet attrs);

    //ripple effect
    float x = -1, y = -1, radius = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        isLastTouch = true;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

        }
        return true;
    }

}

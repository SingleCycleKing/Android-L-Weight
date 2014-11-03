package ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unique.singlecycle.l_weight.R;

import utils.Utils;

/**
 * Created by singlecycle on 10/30/14.
 */
public abstract class Button extends RelativeLayout {
    private int minWidth;
    private int minHeight;
    private int background;
    private float rippleSpeed = 10f;
    private int rippleSize = 3;
    private int rippleColor;
    private int backgroundColor = Color.parseColor("#1E88E5");
    private int disabledBackgroundColor = Color.parseColor("#E2E2E2");
    private boolean isLastTouch = false;
    private boolean customColor = false;
    private OnClickListener onClickListener;

    public Button(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDefaultProperties();
        setAttributes(attrs);
    }

    //获取默认属性
    protected void setDefaultProperties() {
        setMinimumHeight(minHeight);
        setMinimumWidth(minWidth);
        setBackgroundResource(background);
        setBackgroundColor(backgroundColor);

    }

    /**
     * 自定义波纹颜色
     *
     * @param rippleColor
     */
    public void setRippleColor(int rippleColor) {
        this.rippleColor = rippleColor;
        customColor = true;
    }

    /**
     * 自定义波纹尺寸
     *
     * @param rippleSize
     */
    public void setRippleSize(int rippleSize) {
        this.rippleSize = rippleSize;
    }

    /**
     * 自定义波纹扩散速度
     *
     * @param rippleSpeed
     */
    public void setRippleSpeed(float rippleSpeed) {
        this.rippleSpeed = rippleSpeed;
    }

    //从xml上获取属性
    abstract protected void setAttributes(AttributeSet attrs);

    //波纹效果
    float x = -1, y = -1, radius = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        isLastTouch = true;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            radius = getHeight() / rippleSize;
            x = event.getX();
            y = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            radius = getHeight() / rippleSize;
            x = event.getX();
            y = event.getY();
            if (!((event.getX() <= getWidth() && event.getX() >= 0) && (event.getY() <= getHeight() && event.getY() >= 0))) {
                isLastTouch = false;
                x = -1;
                y = -1;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if ((event.getX() <= getWidth() && event.getX() >= 0) &&
                    (event.getY() <= getHeight() && event.getY() >= 0)) {
                radius++;
            } else {
                isLastTouch = false;
                x = -1;
                y = -1;
            }
        }
        return true;
    }

    //改变焦点后
    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        if (!gainFocus) {
            x = -1;
            y = -1;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        return true;
    }

    /**
     * 绘制波纹
     *
     * @return
     */
    public Bitmap makeCircle() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth() - Utils.dpToPx(6, getResources()), getHeight() - Utils.dpToPx(7, getResources()), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawARGB(0, 0, 0, 0);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        if (customColor) paint.setColor(rippleColor);
        else paint.setColor(makePressColor());

        canvas.drawCircle(x, y, radius, paint);
        if (radius > getHeight() / rippleSize)
            radius += rippleSpeed;
        if (radius >= getWidth()) {
            x = -1;
            y = -1;
            radius = getHeight() / rippleSize;
            if (onClickListener != null)
                onClickListener.onClick(this);
        }
        return bitmap;
    }

    //获取默认颜色
    protected int makePressColor() {
        int r = (this.backgroundColor >> 16) & 0xFF;
        int g = (this.backgroundColor >> 8) & 0xFF;
        int b = (this.backgroundColor) & 0xFF;
        r = (r - 30 < 0) ? 0 : r - 30;
        g = (g - 30 < 0) ? 0 : g - 30;
        b = (b - 30 < 0) ? 0 : b - 30;
        return Color.rgb(r, g, b);
    }

    @Override
    public void setOnClickListener(OnClickListener listener) {
        onClickListener = listener;
    }


    abstract public TextView getTextView();
}
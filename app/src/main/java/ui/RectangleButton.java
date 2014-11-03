package ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unique.singlecycle.l_weight.R;

import utils.DeBugLog;
import utils.Utils;

/**
 * Created by singlecycle on 11/3/14.
 */
public class RectangleButton extends RelativeLayout {
    private TextView textView;
    public int minWidth;
    public int minHeight;
    public int background;
    public float rippleSpeed = 10f;
    private int rippleSize = 3;
    private int rippleColor;
    public int backgroundColor = Color.parseColor("#1E88E5");
    private OnClickListener onClickListener;
    private float x = -1, y = -1, radius = -1;

    public RectangleButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        minWidth = 80;
        minHeight = 36;
        background = R.drawable.background_button_rectangle;
        rippleSpeed = Utils.dpToPx(3, getResources());

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RectangleButton);
        String text;
        try {
            text = array.getString(R.styleable.RectangleButton_text);
            backgroundColor = array.getColor(R.styleable.RectangleButton_background, Color.parseColor("#1E88E5"));
            rippleSpeed = array.getFloat(R.styleable.RectangleButton_rippleSpeed, 10f);
            rippleSize = array.getInt(R.styleable.RectangleButton_rippleSize, 3);
            rippleColor = array.getColor(R.styleable.RectangleButton_rippleColor, makePressColor());


        } finally {
            array.recycle();
        }
        setBackgroundColor(backgroundColor);
        textView = new TextView(getContext());

        if (null != text) textView.setText(text);
        else textView.setText("");

        textView.setTextColor(Color.WHITE);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        textView.setLayoutParams(params);
        addView(textView);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            radius = getHeight() / rippleSize;
            x = event.getX();
            y = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            radius = getHeight() / rippleSize;
            x = event.getX();
            y = event.getY();
            if (!((event.getX() <= getWidth() && event.getX() >= 0) && (event.getY() <= getHeight() && event.getY() >= 0))) {
                x = -1;
                y = -1;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if ((event.getX() <= getWidth() && event.getX() >= 0) &&
                    (event.getY() <= getHeight() && event.getY() >= 0)) {
                radius++;
            } else {
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


    //绘制波纹
    private Bitmap makeCircle() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawARGB(0, 0, 0, 0);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        paint.setColor(rippleColor);

        canvas.drawCircle(x, y, radius, paint);
        if (radius > getHeight() / rippleSize || radius > getWidth() / rippleSize)
            radius += rippleSpeed;
        if (radius >= (getWidth() * 1.5) && radius > (getHeight() * 1.5)) {
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

    //绘制
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (x != -1) {
            Rect src = new Rect(0, 0, getWidth(), getHeight());
            Rect dst = new Rect(Utils.dpToPx(0, getResources()), 0, getWidth(), getHeight());
            canvas.drawBitmap(makeCircle(), src, dst, null);
        }
        invalidate();
    }

    /**
     * 设置文字内容
     *
     * @param text
     */
    public void setText(String text) {
        textView.setText(text);
    }


    /**
     * 设置文字颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        textView.setTextColor(textColor);
    }

    /**
     * 自定义波纹颜色
     *
     * @param rippleColor
     */
    public void setRippleColor(int rippleColor) {
        this.rippleColor = rippleColor;

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
}

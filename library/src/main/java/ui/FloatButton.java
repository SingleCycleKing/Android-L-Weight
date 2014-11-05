package ui;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.unique.singlecycle.mylibrary.R;

import utils.DeBugLog;
import utils.Utils;

/**
 * Created by singlecycle on 11/4/14.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class FloatButton extends RelativeLayout {
    private int sizeIcon = 24;
    private int sizeRadius = 28;
    public int minWidth;
    public int minHeight;
    private float rippleSpeed;
    private int rippleSize;
    private int rippleColor;
    private ImageView icon;
    private Drawable drawableIcon;
    private float x = -1, y = -1, radius = -1;
    private OnClickListener onClickListener;
    private boolean isBottom = false;

    public FloatButton(Context context, AttributeSet attrs) {
        super(context, attrs);


        setBackgroundResource(R.drawable.background_button_float);
        setDefaultProperties();

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FloatButton);
        Drawable backgroundDrawable;
        try {
            rippleSpeed = array.getFloat(R.styleable.FloatButton_floatButton_rippleSpeed, 10f);
            rippleSize = array.getInt(R.styleable.FloatButton_floatButton_rippleSize, 3);
            rippleColor = array.getColor(R.styleable.FloatButton_floatButton_rippleColor, makePressColor());
            drawableIcon = array.getDrawable(R.styleable.FloatButton_floatButton_icon);
            backgroundDrawable = array.getDrawable(R.styleable.FloatButton_floatButton_background);
        } finally {
            array.recycle();
        }

        icon = new ImageView(context);
        if (drawableIcon != null) {
            try {
                icon.setBackground(drawableIcon);
            } catch (NoSuchMethodError e) {
                icon.setBackgroundDrawable(drawableIcon);
            }
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Utils.dpToPx(sizeIcon, getResources()), Utils.dpToPx(sizeIcon, getResources()));
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        icon.setLayoutParams(params);
        addView(icon);

        if (backgroundDrawable != null) setBackground(backgroundDrawable);
    }

    /**
     * 退出动画
     */
    public void floatButtonOut() {
        if (isBottom) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(FloatButton.this, "y", getY(), getY() - 500);
            animator.setInterpolator(new BounceInterpolator());
            animator.setDuration(1000);
            animator.start();
            isBottom = false;
        }
    }

    /**
     * 进入动画
     */
    public void floatButtonIn() {
        if (!isBottom) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(FloatButton.this, "y", getY(), getY() + 500);
            animator.setInterpolator(new BounceInterpolator());
            animator.setDuration(1000);
            animator.start();
            isBottom = true;
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (x != -1) {
            Rect src = new Rect(0, 0, getWidth(), getHeight());
            Rect dst = new Rect(Utils.dpToPx(1, getResources()), Utils.dpToPx(2, getResources()), getWidth() - Utils.dpToPx(1, getResources()), getHeight() - Utils.dpToPx(2, getResources()));
            canvas.drawBitmap(cropCircle(makeCircle()), src, dst, null);
        }
        invalidate();
    }

    protected void setDefaultProperties() {
        rippleSpeed = Utils.dpToPx(2, getResources());
        rippleSize = Utils.dpToPx(5, getResources());
        minWidth = sizeRadius * 2;
        minHeight = sizeRadius * 2;
    }

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
        if (radius >= getWidth() * 1.5 && radius > getHeight() * 1.5) {
            x = -1;
            y = -1;
            radius = getHeight() / rippleSize;
            if (onClickListener != null)
                onClickListener.onClick(this);
        }
        return bitmap;
    }

    protected int makePressColor() {
        int r = (Color.parseColor("#ffff4655") >> 16) & 0xFF;
        int g = (Color.parseColor("#ffff4655") >> 8) & 0xFF;
        int b = (Color.parseColor("#ffff4655")) & 0xFF;
        r = (r - 30 < 0) ? 0 : r - 30;
        g = (g - 30 < 0) ? 0 : g - 30;
        b = (b - 30 < 0) ? 0 : b - 30;
        return Color.rgb(r, g, b);
    }

    public Bitmap cropCircle(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(rippleColor);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
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

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        if (!gainFocus) {
            x = -1;
            y = -1;
        }
    }

    @Override
    public void setOnClickListener(OnClickListener listener) {
        onClickListener = listener;
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }

    public Drawable getDrawableIcon() {
        return drawableIcon;
    }

    public void setDrawableIcon(Drawable drawableIcon) {
        this.drawableIcon = drawableIcon;
        try {
            icon.setBackground(drawableIcon);
        } catch (NoSuchMethodError e) {
            icon.setBackgroundDrawable(drawableIcon);
        }
    }

    public void setRippleSize(int rippleSize) {
        this.rippleSize = rippleSize;
    }


    public void setRippleSpeed(float rippleSpeed) {
        this.rippleSpeed = rippleSpeed;
    }

    public void setBackgroundPicture(Drawable drawable) {
        this.setBackground(drawable);
    }

    public void setRippleColor(int rippleColor) {
        this.rippleColor = rippleColor;
    }
}

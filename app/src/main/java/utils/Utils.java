package utils;

import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by singlecycle on 10/30/14.
 */
public class Utils {
    /**
     * 将dp转化为px
     *
     * @param dp
     * @param resources
     * @return
     */
    public static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }

    public static int getRelativeTop(View myView) {
        if (myView.getId() == android.R.id.content)
            return myView.getTop();
        else
            return myView.getTop() + getRelativeTop((View) myView.getParent());
    }

    public static int getRelativeLeft(View myView) {
        if (myView.getId() == android.R.id.content)
            return myView.getLeft();
        else
            return myView.getLeft() + getRelativeLeft((View) myView.getParent());
    }
}

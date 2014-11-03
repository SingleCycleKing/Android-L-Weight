package utils;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by singlecycle on 10/30/14.
 */
public class Utils {

    public static float dpToPx(float dp, Resources resources) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }
}

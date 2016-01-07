package lab.funwith.picasso.data;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ConstantLayoutType {

    public static final int IMAGE_SIZE_FIXED = 1;
    public static final int IMAGE_SIZE_NOT_FIXED = IMAGE_SIZE_FIXED + 1;
    public static final int IMAGE_SIZE_MAX_FIXED = IMAGE_SIZE_NOT_FIXED + 1;

    @IntDef({IMAGE_SIZE_FIXED, IMAGE_SIZE_NOT_FIXED, IMAGE_SIZE_MAX_FIXED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
    }


}

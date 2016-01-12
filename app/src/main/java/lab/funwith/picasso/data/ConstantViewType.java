package lab.funwith.picasso.data;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ConstantViewType {

    public static final int PICTURE_LEFT = 1;
    public static final int PICTURE_RIGHT = PICTURE_LEFT + 1;
    public static final int PICTURE_LEFT_ROTATION = PICTURE_RIGHT + 1;
    public static final int PICTURE_RIGHT_ROTATION = PICTURE_LEFT_ROTATION + 1;
    public static final int PICTURE_LEFT_ROTATION_01 = PICTURE_RIGHT_ROTATION + 1;
    public static final int PICTURE_RIGHT_ROTATION_01 = PICTURE_LEFT_ROTATION_01 + 1;
    public static final int PICTURE_LEFT_BLUR = PICTURE_RIGHT_ROTATION_01 + 1;
    public static final int PICTURE_RIGHT_BLUR = PICTURE_LEFT_BLUR + 1;
    public static final int PICTURE_LEFT_BLUR_GRAY_SCALE = PICTURE_RIGHT_BLUR + 1;
    public static final int PICTURE_RIGHT_BLUR_GRAY_SCALE = PICTURE_LEFT_BLUR_GRAY_SCALE + 1;

    @IntDef({PICTURE_LEFT, PICTURE_RIGHT, PICTURE_RIGHT_ROTATION, PICTURE_LEFT_ROTATION,
            PICTURE_LEFT_BLUR_GRAY_SCALE, PICTURE_RIGHT_BLUR_GRAY_SCALE,
            PICTURE_LEFT_ROTATION_01, PICTURE_RIGHT_ROTATION_01, PICTURE_LEFT_BLUR, PICTURE_RIGHT_BLUR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
    }


}

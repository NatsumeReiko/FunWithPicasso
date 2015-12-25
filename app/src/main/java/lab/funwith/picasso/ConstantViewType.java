package lab.funwith.picasso;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ConstantViewType {

    public static final int PICTURE_LEFT = 0;
    public static final int PICTURE_RIGHT = PICTURE_LEFT + 1;

    @IntDef({PICTURE_LEFT, PICTURE_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
    }


}

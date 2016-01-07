package lab.funwith.picasso.manager;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.squareup.picasso.Picasso;

/**
 * Control Picasso for application.
 */
public class PicassoManager {
    private static PicassoManager instance;
    private static Picasso picasso;

    private PicassoManager() {
        super();
    }

    public static PicassoManager getInstance(
            final Context context) {

        if (instance == null) {
            instance = new PicassoManager();
            picasso = new Picasso.Builder(context).
                    listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            Log.d("testtest exception", exception.toString());
                        }
                    }).
                    build();
        }

        return instance;
    }

    public Picasso getPicasso() {
        return picasso;
    }

}

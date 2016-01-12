package lab.funwith.picasso.model;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import lab.funwith.picasso.R;
import lab.funwith.picasso.data.ConstantUrlData;
import lab.funwith.picasso.data.ConstantViewType;
import lab.funwith.picasso.manager.PicassoManager;

import static android.support.v7.widget.RecyclerView.VERTICAL;
import static android.view.View.VISIBLE;
import static lab.funwith.picasso.data.ConstantViewType.PICTURE_LEFT;
import static lab.funwith.picasso.data.ConstantViewType.PICTURE_LEFT_BLUR;
import static lab.funwith.picasso.data.ConstantViewType.PICTURE_LEFT_BLUR_GRAY_SCALE;
import static lab.funwith.picasso.data.ConstantViewType.PICTURE_RIGHT;
import static lab.funwith.picasso.data.ConstantViewType.PICTURE_RIGHT_BLUR;
import static lab.funwith.picasso.data.ConstantViewType.PICTURE_RIGHT_BLUR_GRAY_SCALE;

public class PicassoBlurActivity extends Activity {
    private RecyclerView recycleView;
    private RecycleViewerAdapter adapter;
    private LinearLayoutManager mLayoutManager;

    private Context appContext;
    private LayoutInflater inflater;
    private final List<ModelData> recycleList = new ArrayList<>();

    private Picasso picasso;


    List<Transformation> grayScaleTransformations = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_picasso_in_recycler_view);

        ButterKnife.bind(this);

        appContext = this.getApplicationContext();
        recycleView = (RecyclerView) findViewById(R.id.model_recycler_view);

        picasso = PicassoManager.getInstance(appContext).getPicasso();

        grayScaleTransformations.add(new GrayscaleTransformation(Picasso.with(appContext)));
        grayScaleTransformations.add(new BlurTransformation(appContext));

        makeData();
        setRecyclerView();
    }

    private void makeData() {
        for (int index = 0; index < 10; index++) {
            for (final String url : ConstantUrlData.URLS) {
                final ModelData dataLeft = new ModelData(url, PICTURE_LEFT);
                recycleList.add(dataLeft);

                final ModelData dataLeftblur = new ModelData(url, PICTURE_LEFT_BLUR);
                recycleList.add(dataLeftblur);

                final ModelData dataLeftblurGray = new ModelData(url, PICTURE_LEFT_BLUR_GRAY_SCALE);
                recycleList.add(dataLeftblurGray);

                /**/

                final ModelData dataRight = new ModelData(url, PICTURE_RIGHT);
                recycleList.add(dataRight);

                final ModelData dataRightBlur = new ModelData(url, ConstantViewType.PICTURE_RIGHT_BLUR);
                recycleList.add(dataRightBlur);

                final ModelData dataRightBlurGray = new ModelData(url, ConstantViewType.PICTURE_RIGHT_BLUR_GRAY_SCALE);
                recycleList.add(dataRightBlurGray);

            }
        }

    }

    private void setRecyclerView() {
        mLayoutManager = new LinearLayoutManager(appContext, VERTICAL, false);
        mLayoutManager.setSmoothScrollbarEnabled(true);
        mLayoutManager.setStackFromEnd(true);

        recycleView.setLayoutManager(mLayoutManager);

        adapter = new RecycleViewerAdapter();
        recycleView.setAdapter(adapter);
        recycleView.setVisibility(VISIBLE);

    }

    //picture view holder
    private static class PictureViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;
        View parent;

        public PictureViewHolder(View v) {
            super(v);

            parent = v;
            picture = (ImageView) v.findViewById(R.id.picture_view);

        }
    }


    //adapter
    public class RecycleViewerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        public RecycleViewerAdapter() {
            inflater = LayoutInflater.from(appContext);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
            final View v = inflater
                    .inflate(getLayoutID(viewType), parent, false);

            switch (viewType) {
                case PICTURE_LEFT:
                case PICTURE_RIGHT:
                case PICTURE_LEFT_BLUR:
                case PICTURE_RIGHT_BLUR:
                case PICTURE_LEFT_BLUR_GRAY_SCALE:
                case PICTURE_RIGHT_BLUR_GRAY_SCALE:

                    return new PictureViewHolder(v);

                default:
                    throw new IllegalArgumentException("No Such ViewHolder");
            }
        }

        private int getLayoutID(final int type) {
            switch (type) {
                case PICTURE_LEFT_BLUR_GRAY_SCALE:
                case PICTURE_LEFT_BLUR:
                case PICTURE_LEFT:
                    return R.layout.picture_left_item_fixed_size;
                case PICTURE_RIGHT_BLUR_GRAY_SCALE:
                case PICTURE_RIGHT_BLUR:
                case PICTURE_RIGHT:
                    return R.layout.picture_right_item_fixed_size;
                default:
                    throw new IllegalArgumentException("No Such type");
            }
        }

        @Override
        public int getItemViewType(final int position) {
            synchronized (recycleList) {
                return recycleList.get(position).viewType;
            }
        }

        private ModelData getTalkMessageResponse(final int position) {
            synchronized (recycleList) {
                return recycleList.get(position);
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof PictureViewHolder) {
                setPictureViewHolder((PictureViewHolder) holder, getTalkMessageResponse(position));
            }

        }

        private void setPictureViewHolder(final PictureViewHolder holder, final ModelData item) {
            switch (item.viewType) {
                case PICTURE_LEFT:
                case PICTURE_RIGHT: {
                    picasso.load(item.pictureUrl)
                            .placeholder(R.color.color_ff886622)
                            .error(R.color.color_fff16622)
                            .into(holder.picture);
                }
                break;
                case PICTURE_LEFT_BLUR:
                case PICTURE_RIGHT_BLUR: {
                    picasso.load(item.pictureUrl)
                            .placeholder(R.color.color_ff886622)
                            .transform(new BlurTransformation(appContext))
                            .error(R.color.color_fff16622)
                            .into(holder.picture);

                }
                break;
                case PICTURE_LEFT_BLUR_GRAY_SCALE:
                case PICTURE_RIGHT_BLUR_GRAY_SCALE: {
                    picasso.load(item.pictureUrl)
                            .placeholder(R.color.color_ff886622)
                            .transform(grayScaleTransformations)
                            .error(R.color.color_fff16622)
                            .into(holder.picture);

                }
                break;
                default:
                    throw new IllegalArgumentException("No Such type");
            }
        }


        @Override
        public int getItemCount() {
            synchronized (recycleList) {
                return recycleList.size();
            }
        }
    }

}

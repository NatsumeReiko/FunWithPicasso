package lab.funwith.picasso.model;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import lab.funwith.picasso.R;
import lab.funwith.picasso.data.ConstantLayoutType;
import lab.funwith.picasso.data.ConstantUrlData;
import lab.funwith.picasso.manager.PicassoManager;

import static android.support.v7.widget.RecyclerView.VERTICAL;
import static android.view.View.VISIBLE;
import static lab.funwith.picasso.data.ConstantViewType.PICTURE_LEFT;
import static lab.funwith.picasso.data.ConstantViewType.PICTURE_RIGHT;

public class PicassoInRecyclerViewActivity extends Activity {
    private static final int IMAGE_MAX_WIDTH = 100;
    private static final int IMAGE_MAX_HEIGHT = 150;

    private RecyclerView recycleView;
    private RecycleViewerAdapter adapter;
    private LinearLayoutManager mLayoutManager;

    private Context appContext;
    private LayoutInflater inflater;
    private final List<ModelData> recycleList = new ArrayList<>();

    private Picasso picasso;

    @ConstantLayoutType.ViewType
    private int currentImageSizeMode = ConstantLayoutType.IMAGE_SIZE_FIXED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_picasso_in_recycler_view);

        ButterKnife.bind(this);

        appContext = this.getApplicationContext();
        recycleView = (RecyclerView) findViewById(R.id.model_recycler_view);

        picasso = PicassoManager.getInstance(appContext).getPicasso();

        makeData();
        setRecyclerView();
        setRadioGroup();

    }

    private void setRadioGroup() {
        final RadioButton radioButton1 = ButterKnife.findById(this, R.id.radio_1);
        radioButton1.setText("size fixed");

        final RadioButton radioButton2 = ButterKnife.findById(this, R.id.radio_2);
        radioButton2.setText("size not fixed");

        final RadioButton radioButton3 = ButterKnife.findById(this, R.id.radio_3);
        radioButton3.setText("max size fixed");

        final RadioGroup radioGroup = ButterKnife.findById(this, R.id.radio_group_panel_3);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_1: {
                        currentImageSizeMode = ConstantLayoutType.IMAGE_SIZE_FIXED;
                    }
                    break;
                    case R.id.radio_2: {
                        currentImageSizeMode = ConstantLayoutType.IMAGE_SIZE_NOT_FIXED;
                    }
                    break;
                    case R.id.radio_3: {
                        currentImageSizeMode = ConstantLayoutType.IMAGE_SIZE_MAX_FIXED;
                    }
                    break;
                }

                adapter = new RecycleViewerAdapter();
                recycleView.setAdapter(adapter);
            }

        });
    }

    private void makeData() {
        for (int index = 0; index < 10; index++) {
            for (final String url : ConstantUrlData.URLS) {
                final ModelData dataLeft = new ModelData(url, PICTURE_LEFT);
                recycleList.add(dataLeft);

                final ModelData dataRight = new ModelData(url, PICTURE_RIGHT);
                recycleList.add(dataRight);
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
                    return new PictureViewHolder(v);

                default:
                    throw new IllegalArgumentException("No Such ViewHolder");
            }
        }

        private int getLayoutID(final int type) {
            switch (type) {
                case PICTURE_LEFT:
                    if (currentImageSizeMode == ConstantLayoutType.IMAGE_SIZE_FIXED) {
                        return R.layout.picture_left_item_fixed_size;
                    } else if (currentImageSizeMode == ConstantLayoutType.IMAGE_SIZE_MAX_FIXED) {
                        return R.layout.picture_left_item_max_fixed_size;
                    } else {
                        return R.layout.picture_left_item_not_fixed_size;
                    }
                case PICTURE_RIGHT:
                    if (currentImageSizeMode == ConstantLayoutType.IMAGE_SIZE_FIXED) {
                        return R.layout.picture_right_item_fixed_size;
                    } else if (currentImageSizeMode == ConstantLayoutType.IMAGE_SIZE_MAX_FIXED) {
                        return R.layout.picture_right_item_max_fixed_size;
                    } else {
                        return R.layout.picture_right_item_not_fixed_size;
                    }
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

        private String getTalkMessageResponse(final int position) {
            synchronized (recycleList) {
                return recycleList.get(position).pictureUrl;
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            final String itemUrl = getTalkMessageResponse(position);

            if (holder instanceof PictureViewHolder) {
                setPictureViewHolder((PictureViewHolder) holder, itemUrl);
            }

        }

        private void setPictureViewHolder(final PictureViewHolder holder, final String pictureUrl) {
            Log.d("testtest", pictureUrl);
            if (currentImageSizeMode == ConstantLayoutType.IMAGE_SIZE_MAX_FIXED) {

                final int size = (int) Math.ceil(Math.sqrt(IMAGE_MAX_WIDTH * IMAGE_MAX_HEIGHT));

                picasso.load(pictureUrl)
                        .placeholder(R.color.color_ff886622)
                        .transform(new BitmapTransform(IMAGE_MAX_WIDTH, IMAGE_MAX_HEIGHT))
                        .resize(size, size)
                        .error(R.color.color_fff16622)
                        .centerInside()
                        .into(holder.picture);
            } else {

                picasso.load(pictureUrl)
                        .placeholder(R.color.color_ff886622)
                        .error(R.color.color_fff16622)
                        .into(holder.picture);
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

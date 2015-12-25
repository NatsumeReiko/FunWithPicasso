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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import lab.funwith.picasso.R;

import static android.support.v7.widget.RecyclerView.VERTICAL;
import static lab.funwith.picasso.ConstantViewType.PICTURE_LEFT;
import static lab.funwith.picasso.ConstantViewType.PICTURE_RIGHT;

public class PicassoInRecyclerViewActivity extends Activity {

    private RecyclerView recycleView;
    private RecycleViewerAdapter adapter;
    private LinearLayoutManager mLayoutManager;

    private Context appContext;
    private LayoutInflater inflater;
    private final List<ModelData> recycleList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso_in_recycler_view);

        appContext = this.getApplicationContext();
        recycleView = (RecyclerView) findViewById(R.id.model_recycler_view);

        makeData();
        setRecyclerView();

    }

    private void makeData() {
        final String BASE = "http://i.imgur.com/";
        final String EXT = ".jpg";
        final String[] URLS = {
                BASE + "CqmBjo5" + EXT, BASE + "zkaAooq" + EXT, BASE + "0gqnEaY" + EXT,
                BASE + "9gbQ7YR" + EXT, BASE + "aFhEEby" + EXT, BASE + "0E2tgV7" + EXT,
                BASE + "P5JLfjk" + EXT, BASE + "nz67a4F" + EXT, BASE + "dFH34N5" + EXT,
                BASE + "FI49ftb" + EXT, BASE + "DvpvklR" + EXT, BASE + "DNKnbG8" + EXT,
                BASE + "yAdbrLp" + EXT, BASE + "55w5Km7" + EXT, BASE + "NIwNTMR" + EXT,
                BASE + "DAl0KB8" + EXT, BASE + "xZLIYFV" + EXT, BASE + "HvTyeh3" + EXT,
                BASE + "Ig9oHCM" + EXT, BASE + "7GUv9qa" + EXT, BASE + "i5vXmXp" + EXT,
                BASE + "glyvuXg" + EXT, BASE + "u6JF6JZ" + EXT, BASE + "ExwR7ap" + EXT,
                BASE + "Q54zMKT" + EXT, BASE + "9t6hLbm" + EXT, BASE + "F8n3Ic6" + EXT,
                BASE + "P5ZRSvT" + EXT, BASE + "jbemFzr" + EXT, BASE + "8B7haIK" + EXT,
                BASE + "aSeTYQr" + EXT, BASE + "OKvWoTh" + EXT, BASE + "zD3gT4Z" + EXT,
                BASE + "z77CaIt" + EXT,
        };

        for (int index = 0; index < 10; index++) {
            for (final String url : URLS) {
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

        recycleView.setLayoutManager(mLayoutManager);

        adapter = new RecycleViewerAdapter();
        recycleView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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

        private int getLayoutID(int type) {
            switch (type) {
                case PICTURE_LEFT:
                    return R.layout.picture_left_item;
                case PICTURE_RIGHT:
                    return R.layout.picture_right_item;
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
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            final ModelData item = getTalkMessageResponse(position);

            if (holder instanceof PictureViewHolder) {
                setPictureViewHolder((PictureViewHolder) holder, item);
                return;
            }

        }

        private void setPictureViewHolder(PictureViewHolder holder, ModelData item) {
            final String url = item.pictureUrl;
            Log.d("testtest",url);

            Picasso.with(appContext)
                    .load(item.pictureUrl)
                    .centerInside()
                    .into(holder.picture);
        }


        @Override
        public int getItemCount() {
            synchronized (recycleList) {
                return recycleList.size();
            }
        }
    }

}

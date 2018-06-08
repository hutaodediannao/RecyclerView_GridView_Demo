package com.app.stethotest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class MyGridLayout extends FrameLayout {

    private GridView gridView;
    private List<String> mImgList;
    private ImageAdapter adapter;

    public MyGridLayout(@NonNull Context context) {
        this(context, null);
    }

    public MyGridLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
       View view = LayoutInflater.from(context).inflate(R.layout.grid_lay, null, false);
       this.addView(view);

        gridView = findViewById(R.id.gridView);
        mImgList = new ArrayList<>();
        adapter = new ImageAdapter();
        gridView.setAdapter(adapter);
    }

    public void updateUI(List<String> imgPathList) {
        this.mImgList.clear();
        this.mImgList.addAll(imgPathList);
        adapter.notifyDataSetChanged();
    }

    public class ImageAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mImgList.size();
        }

        @Override
        public Object getItem(int position) {
            return mImgList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (null == convertView) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lay, null, false);
                vh = new ViewHolder();
                vh.iv = convertView.findViewById(R.id.iv);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            Glide.with(getContext())
                    .load(mImgList.get(position))
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(vh.iv);

            return convertView;
        }

        class ViewHolder {
            ImageView iv;
        }
    }
}

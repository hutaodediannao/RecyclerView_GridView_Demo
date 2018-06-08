package com.app.stethotest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class ImageViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> viewSparseArray;
    private View mItemView;
    private Context mContext;

    public ImageViewHolder(View itemView, Context context) {
        super(itemView);
        this.mItemView = itemView;
        this.mContext = context;
        this.viewSparseArray = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        T t = (T) viewSparseArray.get(viewId);
        if (t == null) {
            t = mItemView.findViewById(viewId);
            viewSparseArray.put(viewId, t);
        }
        return t;
    }

    public ImageViewHolder setImageView(int viewId, String imgPath) {
        ImageView iv = getView(viewId);
        Glide.with(mContext)
                .load(imgPath)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(iv);
        return this;
    }

    public ImageViewHolder setGridLayoutUI(int viewId, List<String> imgPath) {
        MyGridLayout mg = getView(viewId);
        mg.updateUI(imgPath);
        return this;
    }

}

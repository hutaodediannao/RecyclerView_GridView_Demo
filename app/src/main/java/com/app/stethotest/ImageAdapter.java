package com.app.stethotest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private List<Model> modelList;
    private Context mContext;
    private int layoutId;

    public ImageAdapter(List<Model> models, Context mContext, int layoutId) {
        this.modelList = models;
        this.mContext = mContext;
        this.layoutId = layoutId;
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        return new ImageViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
//        holder.setImageView(R.id.iv, mImgList.get(position));

        holder.setIsRecyclable(false);//false - 禁止复用 true-可以复用
        holder.setGridLayoutUI(R.id.myGridLayout, modelList.get(position).getImgList());
    }

}

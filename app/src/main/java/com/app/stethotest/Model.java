package com.app.stethotest;

import java.util.List;

public class Model {

    private List<String> imgList;

    public Model(List<String> imgList) {
        this.imgList = imgList;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
}

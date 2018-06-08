package com.app.stethotest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mylibrary.LIBActivity;

import org.mozilla.javascript.tools.jsc.Main;
import org.reactivestreams.Subscription;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private List<Model> modelList = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private ProgressDialog progressDialog;
    private static String BASE_IMG_PATH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            BASE_IMG_PATH = Environment.getExternalStorageDirectory().getCanonicalPath() + "/A-HenDaDowloadImg";
        } catch (IOException e) {
            e.printStackTrace();
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        imageAdapter = new ImageAdapter(modelList, this, R.layout.model_lay);
        recyclerView.setAdapter(imageAdapter);

        progressDialog = ProgressDialog.show(this, "正在请求", "正在加载文件夹");
        requestData();
    }

    public void requestData() {
        progressDialog.show();
        Observable.create(new ObservableOnSubscribe<List<Model>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Model>> emitter) throws Exception {
                emitter.onNext(loadImgList(BASE_IMG_PATH));
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Model>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Model> strings) {
                        modelList.clear();
                        modelList.addAll(strings);
                    }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onComplete() {
                        imageAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                });
    }


    /**
     * 从文件夹中读取图片文件列表
     */
    public List<Model> loadImgList(String path) {
        List<Model> models = new ArrayList<>();
        File file = new File(path);

        String[] imgArray = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name == null) return false;
                return name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith("jpeg") || name.endsWith("JPEG") || name.endsWith("bmp") || name.endsWith("tiff") || name.endsWith("gif");
            }
        });

        for (int i = 0; i < imgArray.length-2; i+=3) {
            List<String> imgList = new ArrayList<>();
            imgList.add(BASE_IMG_PATH + "/" + imgArray[i]);
            imgList.add(BASE_IMG_PATH + "/" + imgArray[i+1]);
            imgList.add(BASE_IMG_PATH + "/" + imgArray[i+2]);

            Model m = new Model(imgList);
            models.add(m);
        }

        return models;
    }

}

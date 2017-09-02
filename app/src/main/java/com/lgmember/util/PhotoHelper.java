package com.lgmember.util;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TakePhotoOptions;

import java.io.File;

/**
 * Created by Mickey on 2017/8/28.
 */

public class PhotoHelper {
    private View rootView;


    public PhotoHelper(View rootView) {
        this.rootView = rootView;
    }

    public static PhotoHelper of(View rootView, Context context){
        return new PhotoHelper(rootView);
    }

    public void onCamera(View view, TakePhoto photo){
        File file=new File(Environment.getExternalStorageDirectory(),
                "/temp"+ System.currentTimeMillis()+".jpg");

        if (!file.getParentFile().exists())file.getParentFile().mkdirs();
        Uri imageUri= Uri.fromFile(file);

        configCompress(photo);
        configTakePhotoOption(photo);
        photo.onPickFromCaptureWithCrop(imageUri,getCropOptions());
    }
    public void onPicture(View view, TakePhoto photo){
        File file=new File(Environment.getExternalStorageDirectory(),
                "/temp"+ System.currentTimeMillis()+".jpg");

        if (!file.getParentFile().exists())file.getParentFile().mkdirs();
        Uri imageUri= Uri.fromFile(file);

        configCompress(photo);
        configTakePhotoOption(photo);

        photo.onPickFromGalleryWithCrop(imageUri,getCropOptions());

    }
    //裁剪图片属性
    private CropOptions getCropOptions() {
        CropOptions.Builder builder=new CropOptions.Builder();
        builder.setAspectX(800).setAspectY(800);//裁剪时的尺寸比例
        builder.setWithOwnCrop(true);//s使用第三方还是takephoto自带的裁剪工具
        return builder.create();
    }

    //配置图片属性
    private void configTakePhotoOption(TakePhoto photo) {
        TakePhotoOptions.Builder builder=new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);//使用自带相册
        builder.setCorrectImage(false);//纠正旋转角度
        photo.setTakePhotoOptions(builder.create());
    }

    //    配置压缩
    private void configCompress(TakePhoto takePhoto){
        CompressConfig config=new CompressConfig.Builder()
                .setMaxSize(102400)//大小不超过100k
                .setMaxPixel(800)//最大像素800
                .enableReserveRaw(true)//是否压缩
                .create();
        takePhoto.onEnableCompress(config,true);//这个trued代表显示压缩进度条
    }
}
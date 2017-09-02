package com.lgmember.activity.person;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.BaseActivity1;
import com.lgmember.activity.MainActivity;
import com.lgmember.activity.R;
import com.lgmember.business.ShowNetworkImgBusiness;
import com.lgmember.business.UpdatePhotoBusiness;
import com.lgmember.business.message.MemberMessageBusiness;
import com.lgmember.model.Member;
import com.lgmember.util.MyUtils;
import com.lgmember.util.PhotoHelper;
import com.lgmember.view.TopBarView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Yanan_Wu on 2017/4/5.
 */

public class PersonalAllActivity1 extends BaseActivity1 implements TopBarView.onTitleBarClickListener,View.OnClickListener ,UpdatePhotoBusiness.UpdatePhotoResulHandler,MemberMessageBusiness.MemberMessageResulHandler,ShowNetworkImgBusiness.ShowNetworkImgResulHandler{

    private TopBarView topBar;
    private RelativeLayout rl_photo,rl_personal,rl_certification;
    private ImageView iv_photo;
    private String phone;
    String path;
    Bitmap bitmap;
    PhotoHelper photoHelper;
    private int flagAuthorized ;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View contentView= LayoutInflater.from(this).inflate(R.layout.activity_personal_all,null);
        setContentView(contentView);
        init();
        getData();
        photoHelper= PhotoHelper.of(contentView,this);
    }

    private void init() {
        iv_photo = (ImageView)findViewById(R.id.iv_photo);
        topBar = (TopBarView)findViewById(R.id.topbar);
        topBar.setClickListener(this);

        rl_photo = (RelativeLayout)findViewById(R.id.rl_photo);
        rl_personal = (RelativeLayout)findViewById(R.id.rl_personal);
        rl_certification = (RelativeLayout)findViewById(R.id.rl_certification);

        rl_photo.setOnClickListener(this);
        rl_personal.setOnClickListener(this);
        rl_certification.setOnClickListener(this);

    }

    private void getData() {
        MemberMessageBusiness memberMessage = new MemberMessageBusiness(context);
        memberMessage.setHandler(this);
        memberMessage.getMemberMessage();
    }

    @Override
    public void onBackClick() {
        startIntent(MainActivity.class);
    }

    @Override
    public void onRightClick() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_photo:
                //更新头像
                selectFromPicture(v);
                break;
            case R.id.rl_personal:
                //更新会员信息
                startIntent(PersonalActivity.class);
                break;
            case R.id.rl_certification:
                if (flagAuthorized == 1){
                    showToast("您已通过实名认证");
                }else if (flagAuthorized == 2){
                    showToast("您已提交实名认证,正在审核中");
                }else {
                    Intent intent = new
                            Intent(PersonalAllActivity1.this,
                            CertificationActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("phone",phone);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
        }
    }
    public void  selectFromPicture(View v){
        final View view=v ;
        final String[] items=new String[]{"拍照","相册"};
        new AlertDialog.Builder(PersonalAllActivity1.this).setTitle("选择").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        // 调用系统的拍照功能
                        photoHelper.onCamera(view,getTakePhoto());
                        break;
                    case 1:
                        //调用系统图库
                        photoHelper.onPicture(view,getTakePhoto());
                        break;
                }
            }
        }).show();
    }
    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showImg(result.getImages());
    }

    private void showImg(final ArrayList<TImage> images) {
        path=images.get(images.size() - 1).getCompressPath();

        File file = new File(path);
        if (file.exists()) {
            UpdatePhoto(file);
            Bitmap bm = BitmapFactory.decodeFile(path);
            iv_photo.setBackground(new BitmapDrawable(context.getResources(),bm));
        }
    }
    private void showNetworkImg(String photoName) {

        ShowNetworkImgBusiness showNetworkImgBusiness = new ShowNetworkImgBusiness(context,photoName);
        showNetworkImgBusiness.setHandler(this);
        showNetworkImgBusiness.showNetworkImg();

    }
    private void UpdatePhoto(File file) {
        UpdatePhotoBusiness updatePhotoBusiness = new UpdatePhotoBusiness(context,file);
        updatePhotoBusiness.setHandler(this);
        updatePhotoBusiness.updatePhoto();
    }

    //更新头像
    @Override
    public void onSuccess() {
        showToast("上传成功");

    }

    @Override
    public void onShowImgSuccess() {

        String path = Environment.getExternalStorageDirectory() + "/network.jpg";
        Bitmap bm = BitmapFactory.decodeFile(path);
        iv_photo.setBackground(new BitmapDrawable(context.getResources(),bm));
    }

    @Override
    public void onSuccess(Member member) {

        //后台传过来的图片为空，设置为默认的，否则，就用后台传过来的
        if (member.getAvatar() == null ||
                member.getAvatar().isEmpty()){
            iv_photo.setImageResource(R.drawable.touxiang);
        }else {
            showNetworkImg(member.getAvatar());
        }

        phone = member.getMobile();

        flagAuthorized =  member.getAuthorized();
    }

    @Override
    public void onShowImgFailed(String s) {

        iv_photo.setBackground(getResources().getDrawable(R.drawable.touxiang));
    }
}

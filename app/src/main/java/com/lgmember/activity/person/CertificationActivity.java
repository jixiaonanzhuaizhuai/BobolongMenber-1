package com.lgmember.activity.person;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.LoginActivity;
import com.lgmember.activity.MainActivity;
import com.lgmember.activity.R;
import com.lgmember.business.SmsCodeBusiness;
import com.lgmember.business.UploadImgBusiness;
import com.lgmember.business.message.MemberMessageBusiness;
import com.lgmember.business.person.CertificationBusiness;
import com.lgmember.model.Certification;
import com.lgmember.model.Member;
import com.lgmember.util.StringUtil;
import com.lgmember.view.TopBarView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Yanan_Wu on 2017/1/5.
 */

public class CertificationActivity extends BaseActivity
        implements OnClickListener, CertificationBusiness.CertificationResulHandler ,UploadImgBusiness.UploadImgResulHandler,TopBarView.onTitleBarClickListener,SmsCodeBusiness.GetCodeResultHandler,MemberMessageBusiness.MemberMessageResulHandler {

    private int REQUEST_CODE = 0;
    private Button uploadImgBtn, commitBtn;
    private ImageView imgShow;
    private EditText nameEdt,IDcardEdt;
    private TextView txt_mobile;
    private Spinner sp_nation,sp_gender;
    private Context context;
    private int nation;
    private int gender;
    private String session_id;
    private String TAG = "-CertificationActivity-";
    private TopBarView topBar;
    private String capt_token;

    private String outPath;

    private boolean flag = false;
    private boolean flagDiag = true;

    private ArrayAdapter<String> nationAdapt,genderAdapter;

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    private String phone;

    private static final int REQUEST_CODE_PERMISSION_CAMERA = 100;
    private static final int REQUEST_CODE_SETTING = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
        context = this;
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMemberData();
    }

    private void getMemberData() {
        MemberMessageBusiness memberMessage = new MemberMessageBusiness(context);
        memberMessage.setHandler(this);
        memberMessage.getMemberMessage();
    }

    private void init() {
        Bundle bundle = this.getIntent().getExtras();
        phone = bundle.getString("phone");
        sharedPreferences = this.getSharedPreferences("certificationInfo", Context.MODE_PRIVATE);
        topBar = (TopBarView)findViewById(R.id.topbar);
        topBar.setClickListener(this);
        uploadImgBtn = (Button) findViewById(R.id.uploadImgBtn);
        commitBtn = (Button) findViewById(R.id.commitBtn);
        imgShow = (ImageView) findViewById(R.id.imgShow);
        nameEdt = (EditText)findViewById(R.id.nameEdt);
        IDcardEdt = (EditText)findViewById(R.id.IDcardEdt);
        txt_mobile = (TextView) findViewById(R.id.txt_mobile) ;
        sp_nation = (Spinner) findViewById(R.id.sp_nation);
        nationAdapt = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,StringUtil.NATIONS);
        nationAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_nation.setAdapter(nationAdapt);
        sp_nation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nation = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_gender = (Spinner) findViewById(R.id.sp_gender);
        genderAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,StringUtil.GENDER);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gender.setAdapter(genderAdapter);
        sp_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = position;

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nameEdt.setText(sharedPreferences.getString("userName",null));
        IDcardEdt.setText(sharedPreferences.getString("Idno",null));
        txt_mobile.setText(phone);
        sp_nation.setSelection(sharedPreferences.getInt("nation",0));
        sp_gender.setSelection(sharedPreferences.getInt("gender",0));
        Bitmap bm = BitmapFactory.decodeFile(sharedPreferences.getString("img",null));
        imgShow.setVisibility(View.VISIBLE);
        imgShow.setImageBitmap(bm);

        uploadImgBtn.setOnClickListener(this);
        commitBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.uploadImgBtn:
                loadImgPermission();
                //uploadImg();
                break;
            case R.id.commitBtn:
                //DialogPhoneCode();
                commit();
                break;
        }
    }

    private void loadImgPermission() {
        // 申请单个权限。
        AndPermission.with(this)
                .requestCode(REQUEST_CODE_PERMISSION_CAMERA)
                .permission(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                .callback(permissionListener)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限。
                // 你也可以不设置。
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(CertificationActivity.this, rationale).
                                show();
                    }
                })
                .start();
    }

    public void commit(){
        Certification certification = new Certification();
        certification.setName(getText(nameEdt));
        certification.setIdno(getText(IDcardEdt));
        certification.setNation(nation);
        certification.setUpload_session_id(session_id);
        boolean genderBoolean;
        if (gender == 0){
            genderBoolean = true;
        }else {
            genderBoolean = false;
        }
        certification.setGender(genderBoolean);
        /*certification.setCapt(capt);
        certification.setCapt_token(capt_token);*/

        editor = sharedPreferences.edit();
        editor.putString("userName", getText(nameEdt));
        editor.putString("Idno",getText(IDcardEdt));
        editor.putInt("nation",nation);
        editor.putInt("gender",gender);
        editor.putString("img",outPath);
        editor.commit();

        CertificationBusiness certificationBusiness = new CertificationBusiness(context,certification);
        certificationBusiness.setHandler(this);
        certificationBusiness.certificationMsg();
    }

    //上传图片
    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    };
    public void uploadImg(){
        ImgSelConfig config = new ImgSelConfig.Builder(this, loader)
                // 是否多选
                .multiSelect(false)
                .btnText("Confirm")
                // 确定按钮背景色
                //.btnBgColor(Color.parseColor(""))
                // 确定按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#3F51B5"))
                // 返回图标ResId
                .backResId(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material)
                .title("Images")
                .titleColor(Color.WHITE)
                .titleBgColor(Color.parseColor("#3F51B5"))
                .allImagesText("All Images")
                .cropSize(1, 1, 200, 200)
                .needCrop(false)
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(9)
                .build();
        ImgSelActivity.startActivity(this, config, REQUEST_CODE);
    }

    private void uploadIDImg(File file) {
        session_id = java.util.UUID.randomUUID().toString();

        Log.d(session_id,"****************"+session_id+"****************");
        UploadImgBusiness uploadImgBusiness = new UploadImgBusiness(context,session_id,file);
        uploadImgBusiness.setHandler(this);
        uploadImgBusiness.uploadImg();
    }

    //弹出对话框
    public void showDialog(String s){
        //注册成功后的业务逻辑
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("实名认证通知");
        builder.setMessage("您未通过实名认证，原因是"+s+"，请您重新申请实名认证");
        builder.setPositiveButton("重新申请", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                //完成业务逻辑
                flagDiag = false;
                nameEdt.setText("");
                IDcardEdt.setText("");
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                //业务逻辑
                flagDiag = false;
            }
        });
        builder.show();

    }

    @Override
    public void onSuccess() {
        flag = true;
        showToast("提交成功");
        startIntent(MainActivity.class);
        finish();
    }

    @Override
    public void onUploadImgSuccess() {
        showToast("图片上传成功");
    }

    @Override
    public void onBackClick() {
        if (flag == true){
            startIntent(MainActivity.class);
        }else {
            startIntent(LoginActivity.class);
        }
    }


    @Override
    public void onRightClick() {
    }

    private AlertDialog dialog;
    public void DialogPhoneCode() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        dialog = adb.create();
        View view = getLayoutInflater().inflate(
                R.layout.dialog_exchange_gift_phone_code, null);
        TextView txt_phone = (TextView) view.findViewById(R.id.txt_phone);
        txt_phone.setText(phone);
        final Button btn_request_code = (Button) view.findViewById(R.id.btn_request_code);
        class TimeCount extends CountDownTimer {
            public TimeCount(long millisInFuture, long countDownInterval) {
                super(millisInFuture, countDownInterval);
            }
            public void onFinish() {
                btn_request_code.setText("获取验证码");
                btn_request_code.setClickable(true);
            }
            public void onTick(long millisUntilFinished) {
                btn_request_code.setClickable(false);
                btn_request_code.setText(millisUntilFinished / 1000 + "秒后点击重发");
            }
        }
        final TimeCount timeCount = new TimeCount(60000,1000);
        btn_request_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_request_code.isClickable()) {
                    timeCount.start();
                    getCode();
                }
            }
        });
        final EditText edt_code = (EditText)view.findViewById(R.id.edt_code);
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getText(edt_code).equals("")){
                    showToast("请输入验证码!");
                }else {
                    //commit(getText(edt_code));
                    dialog.dismiss();
                }

            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
    }
    private void getCode(){
        SmsCodeBusiness getCodeBusiness =
                new SmsCodeBusiness(context,phone);
        //处理结果
        getCodeBusiness.setHandler(this);
        getCodeBusiness.getCode();
    }

    @Override
    public void onRequestCodeSuccess(String string) {
        capt_token = string;
    }
    //重写返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            if (flag == true){
                startIntent(MainActivity.class);
            }else {
                startIntent(LoginActivity.class);
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION_CAMERA: {
                    uploadImg();
                    break;
                }
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION_CAMERA: {
                    showToast("获取相机权限失败！！");
                    break;
                }
            }

            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(CertificationActivity.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(CertificationActivity.this, REQUEST_CODE_SETTING).show();

            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SETTING){
            uploadImg();
        }
        //上传图片后的处理
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            for (final String path : pathList) {
                if (!TextUtils.isEmpty(path)) {
                    Glide.with(CertificationActivity.this)
                            .load(path)
                            .override(300,300)
                            .into(imgShow);
                    Bitmap bm = BitmapFactory.decodeFile(path);
                    imgShow.setVisibility(View.VISIBLE);
                    imgShow.setImageBitmap(bm);
                    outPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "certification";
                    try {
                        saveFile(bm,outPath,"00.jpg");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public void saveFile(Bitmap bm,String path, String fileName) throws IOException {
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path , fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        uploadIDImg(myCaptureFile);
    }

    @Override
    public void onSuccess(Member member) {

        if(member.getAuthorized() == 3 && flagDiag == true){
            showDialog(member.getReason());
        }

    }
}


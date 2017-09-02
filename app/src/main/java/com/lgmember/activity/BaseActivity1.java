package com.lgmember.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.jph.takephoto.permission.PermissionManager.TPermissionType;
import com.lgmember.util.ActivityCollector;

public class BaseActivity1 extends AppCompatActivity implements TakePhoto.TakeResultListener,InvokeListener {

	protected Context context;
	private static final String TAG = TakePhotoActivity.class.getName();
	private TakePhoto takePhoto;
	private InvokeParam invokeParam;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		Log.d("-----BaseActivity---",getClass().getSimpleName());
		ActivityCollector.addActivity(this);
		getTakePhoto().onCreate(savedInstanceState);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null){
			actionBar.hide();
		}
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		getTakePhoto().onSaveInstanceState(outState);
		super.onSaveInstanceState(outState);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		getTakePhoto().onActivityResult(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
		PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
	}

	/**
	 *  获取TakePhoto实例
	 * @return
	 */
	public TakePhoto getTakePhoto(){
		if (takePhoto==null){
			takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
		}
		return takePhoto;
	}
	@Override
	public void takeSuccess(TResult result) {
		Log.i(TAG,"takeSuccess：" + result.getImage().getCompressPath());
	}
	@Override
	public void takeFail(TResult result,String msg) {
		Log.i(TAG, "takeFail:" + msg);
	}
	@Override
	public void takeCancel() {
		Log.i(TAG, getResources().getString(R.string.msg_operation_canceled));
	}

	@Override
	public TPermissionType invoke(InvokeParam invokeParam) {
		TPermissionType type= PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
		if(TPermissionType.WAIT.equals(type)){
			this.invokeParam=invokeParam;
		}
		return type;
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}

	public void showToast(String string ) {
		Toast.makeText(BaseActivity1.this, string, Toast.LENGTH_SHORT).show();
	}

	public  void startIntent(Class clazz) {
		Intent intent = new Intent(BaseActivity1.this,clazz);
		startActivity(intent);
	}
	public String getText(TextView v) {
		return v.getText().toString().trim();
	}


	public void onArgumentEmpty(String string) {
		showToast(string);

	}

	public void onError(int code) {
		if (code == 1){
			showLoginDialog();
		}
	}

	public void onNetworkDisconnect() {
		showToast(context.getString(R.string.http_network_disconnect));
	}

	public void onFailed(int code, String msg) {

		//showToast(code + msg+context.getString(R.string.server_error));
	}

	private void showLoginDialog(){
		final AlertDialog.Builder normalDialog =
				new AlertDialog.Builder(BaseActivity1.this);
		normalDialog.setTitle("提示");
		normalDialog.setMessage("需要登录!");
		normalDialog.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//...To-do
						startIntent(LoginActivity.class);
					}
				});
		normalDialog.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//...To-do
					}
				});
		// 显示
		normalDialog.show();
	}




}

package com.lgmember.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.lgmember.AudioRecorder.OnBooleanListener;
import com.lgmember.util.ActivityCollector;
import com.lgmember.util.StatusBarCompat;
import com.lgmember.util.StringUtil;
import android.Manifest;

public class BaseActivity extends AppCompatActivity {

	protected Context context;
	private OnBooleanListener onPermissionListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		StatusBarCompat.compat(this, getResources().getColor(R.color.main_2));//设置顶部状态栏的颜色
		Log.d("-----BaseActivity---",getClass().getSimpleName());
		ActivityCollector.addActivity(this);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null){
			actionBar.hide();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}

	public void showToast(String string ) {
		Toast.makeText(BaseActivity.this, string, Toast.LENGTH_SHORT).show();
	}

	public  void startIntent(Class clazz) {
		Intent intent = new Intent(BaseActivity.this,clazz);
		startActivity(intent);
	}
	public String getText(TextView v) {
		return v.getText().toString().trim();
	}


	public void onArgumentEmpty(String string) {
		showToast(string);

	}

	public void onArgumentFormatError(String string) {
		showToast(string);
	}

	public void onError(int code) {
		if (code == 1){
			showLoginDialog();
		}else {
			showToast(context.getString(StringUtil.numToErrorState(code)));
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
				new AlertDialog.Builder(BaseActivity.this);
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

	/**
	 * 权限请求
	 * @param permission Manifest.permission.CAMERA
	 * @param onBooleanListener 权限请求结果回调，true-通过  false-拒绝
	 */
	public void onPermissionRequests(String permission, OnBooleanListener onBooleanListener) {
		onPermissionListener = onBooleanListener;
		if (ContextCompat.checkSelfPermission(this,
				permission)
				!= PackageManager.PERMISSION_GRANTED) {
			// Should we show an explanation?
			if (ActivityCompat.shouldShowRequestPermissionRationale(this,
					Manifest.permission.READ_CONTACTS)) {
				//权限已有
				onPermissionListener.onClick(true);
			} else {
				//没有权限，申请一下
				ActivityCompat.requestPermissions(this,
						new String[]{permission},
						1);
			}
		}else{
			onPermissionListener.onClick(true);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		if (requestCode == 1) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				//权限通过
				if (onPermissionListener != null) {
					onPermissionListener.onClick(true);
				}
			} else {
				//权限拒绝
				if (onPermissionListener != null) {
					onPermissionListener.onClick(false);
				}
			}
			return;
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}


}

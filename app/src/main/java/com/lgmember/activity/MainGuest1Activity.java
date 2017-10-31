package com.lgmember.activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lgmember.AudioRecorder.AndroidAudioRecorder;
import com.lgmember.AudioRecorder.model.AudioChannel;
import com.lgmember.AudioRecorder.model.AudioSampleRate;
import com.lgmember.AudioRecorder.model.AudioSource;
import com.lgmember.activity.card.MyCardActivity;
import com.lgmember.activity.collection.CollectionActivity;
import com.lgmember.activity.feedback.ReportProblemActivity;
import com.lgmember.activity.message.MyMessageActivity;
import com.lgmember.activity.person.CertificationActivity;
import com.lgmember.activity.person.EditPersonalActivity;
import com.lgmember.activity.person.PersonalAllActivity1;
import com.lgmember.activity.project.ClubActivityAllListActivity;
import com.lgmember.activity.project.ClubProjectListActivity;
import com.lgmember.activity.project.ProjectMessageDetailActivity;
import com.lgmember.activity.project.ProjectMessageManageActivity;
import com.lgmember.activity.score.ExchangeScoresActivity;
import com.lgmember.activity.score.MyScoresActivity;
import com.lgmember.activity.setting.SettingActivity;
import com.lgmember.adapter.MenuListAdapter;
import com.lgmember.adapter.TagsListAdapter;
import com.lgmember.bean.ClubListResultBean;
import com.lgmember.bean.ProjectMessageBean;
import com.lgmember.bean.TagsListResultBean;
import com.lgmember.business.ApkBusiness;
import com.lgmember.business.ShowNetworkImgBusiness;
import com.lgmember.business.VersionBusiness;
import com.lgmember.business.message.MemberMessageBusiness;
import com.lgmember.business.message.RemindNumBusiness;
import com.lgmember.business.project.MyClubListBusiness;
import com.lgmember.business.project.ProjectMessageListBusiness;
import com.lgmember.business.project.TagListBusiness;
import com.lgmember.model.Club;
import com.lgmember.model.Member;
import com.lgmember.model.Menu;
import com.lgmember.model.ProjectMessage;
import com.lgmember.model.Tag;
import com.lgmember.util.ActivityCollector;
import com.lgmember.util.Common;
import com.lgmember.util.CustomLinearLayoutManager;
import com.lgmember.util.DataLargeHolder;
import com.lgmember.util.QRCodeUtil;
import com.lgmember.util.StringUtil;
import com.lgmember.view.BadgeView;
import com.stx.xhb.xbanner.XBanner;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.hwang.widgets.SmartPullableLayout;


public class MainGuest1Activity extends BaseActivity implements OnClickListener,
		ProjectMessageListBusiness.ProjectMessageListResultHandler,
		VersionBusiness.VersionResulHandler,ApkBusiness.ApkResulHandler,MyClubListBusiness.MyClubListResulHandler{
    private TextView cmoreactivity,moreactivity,rmoreactivity,top_tv_login,top_tv_exit,tv_login,tv_register;
	private ImageView iv_menu;
	private XBanner xRecommendBanner,allBanner,myClubBanner;
	private ArrayList<String> myClubImages ;
	private ArrayList<String> allImages ;
	private ArrayList<String> recommendImages;
    private boolean isButton = true;
	private String gender,nation;
	private String birthday;
	private int age;
	private int unReadNum;
	private int pageNo = 1;
	private int pageSize = 3;
	private BadgeView badgeView;
	private int currVersion;

    private String phone;
	private AlertDialog dialog;

	private ProgressDialog progressDialog;

	private SmartPullableLayout mPullableLayout;

	private ListView lsvMore ;//memu列表



	private String TAG = "-MianActivity-";
	private SharedPreferences sp;

	private List<Tag> tagList ;

	private PopupWindow window;
	private MenuListAdapter menuListAdapter;
	private List<Menu> menuList;

	private List<Club> myClubList;
	List<String> myClubTitleList;
	private List<ProjectMessage> projectMessageList;
	List<String> allTitleList;
	private List<ProjectMessage> hotProjectMessageList;
	List<String> recommendTitleList;
	private String[] menuNameDatas = {"个人资料", "活动管理", "俱乐部", "我的积分", "积分兑换","我的卡券","我要签到","我的消息","收藏","问题反馈","设置"};
	private int[] menuImgDatas = {R.mipmap.shouye01,R.mipmap.shouye02,R.mipmap.shouye03,R.mipmap.shouye04,R.mipmap.shouye05,R.mipmap.shouye06,R.mipmap.shouye07,R.mipmap.shouye08,R.mipmap.shouye09,R.mipmap.shouye10,R.mipmap.shouye11};


	private static final int ON_REFRESH = 1;
	private static final int ON_LOAD_MORE = 2;

	private String oldStartTime;
	private int authorized;

	Message message;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case ON_REFRESH:
					mPullableLayout.stopPullBehavior();
					break;
				case ON_LOAD_MORE:
					mPullableLayout.stopPullBehavior();
					break;
				case 1000 :
					progressDialog.setProgress(msg.arg1);
					break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_guest1);
		initView();
	}
	@Override
	protected void onResume() {
		super.onResume();
		getInitData();

	}
	private void getInitData(){
		//录音
		sp = this.getSharedPreferences(Common.SP_NAME, MODE_PRIVATE);
		versionNoUpdateTime();pageNo = 1;

		getMyClubList();
		getProjectMessage();
		getHotProjectMessage();
		getMenuList();

}

	private void getMenuList() {
		menuList.clear();
		for (int i =0;i<menuNameDatas.length; i++){
			Menu menu = new Menu();
			menu.setMenuName(menuNameDatas[i]);
			menu.setMenuImg(menuImgDatas[i]);
			menuList.add(menu);
		}
	}

	private void initView() {
		menuList = new ArrayList<>();
		menuListAdapter = new MenuListAdapter(MainGuest1Activity.this,menuList);
		//构建一个popupwindow的布局
		View popupView = MainGuest1Activity.this.getLayoutInflater().inflate(R.layout.popupwindow, null);

		//为了演示效果，简单的设置了一些数据，实际中大家自己设置数据即可，相信大家都会。
		lsvMore = (ListView) popupView.findViewById(R.id.lsvMore);
		lsvMore.setAdapter(menuListAdapter);

		//创建PopupWindow对象，指定宽度和高度
		window = new PopupWindow(popupView, 600, 1500);
               /* // TODO: 2016/5/17 设置动画
                window.setAnimationStyle(R.style.popup_window_anim);*/
		//设置背景颜色
		window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
		//设置可以获取焦点
		window.setFocusable(true);
		//设置可以触摸弹出框以外的区域
		window.setOutsideTouchable(true);

		top_tv_login = (TextView)findViewById(R.id.top_tv_login);
		top_tv_login.setOnClickListener(this);
		top_tv_exit = (TextView)findViewById(R.id.top_tv_exit);
		top_tv_exit.setOnClickListener(this);

		tv_login = (TextView)findViewById(R.id.tv_login);
		tv_login.setOnClickListener(this);
		tv_register = (TextView)findViewById(R.id.tv_register);
		tv_register.setOnClickListener(this);

		myClubList = new ArrayList<>();
		myClubTitleList = new ArrayList<>();
		projectMessageList = new ArrayList<>();
		allTitleList = new ArrayList<>();
		hotProjectMessageList = new ArrayList<>();
		recommendTitleList = new ArrayList<>();

		progressDialog = new ProgressDialog(this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(true);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setTitle("更新进度提示");

		tagList = new ArrayList<>();
		//获取标签列表数据
		myClubImages = new ArrayList<>();
		allImages = new ArrayList<>();
		recommendImages = new ArrayList<>();
		iv_menu = (ImageView) findViewById(R.id.iv_menu);
		cmoreactivity = (TextView)findViewById(R.id.cmoreactivity);
		moreactivity = (TextView)findViewById(R.id.moreactivity);
		rmoreactivity = (TextView)findViewById(R.id.rmoreactivity);
		allBanner = (XBanner) findViewById(R.id.allBanner);
		xRecommendBanner = (XBanner) findViewById(R.id.recommendBanner);
		myClubBanner = (XBanner)findViewById(R.id.myClubBanner);
		initMyClubBanner();
		initAllBanner();
		initRecommendBanner();

		iv_menu.setOnClickListener(this);
		cmoreactivity.setOnClickListener(this);
		moreactivity.setOnClickListener(this);
		rmoreactivity.setOnClickListener(this);


		mPullableLayout = (SmartPullableLayout)findViewById(R.id.layout_pullable);
		mPullableLayout.setOnPullListener(new SmartPullableLayout.OnPullListener() {
			@Override
			public void onPullDown() {
				getInitData();
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						mHandler.sendEmptyMessage(ON_REFRESH);
					}
				}).start();

			}
			@Override
			public void onPullUp() {
				getInitData();
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						mHandler.sendEmptyMessage(ON_LOAD_MORE);
					}
				}).start();
			}

		});
	}

	/*版本暂不更新，一天提示一次*/
	private void versionNoUpdateTime() {
		Date nowDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = format.format(nowDate);
		oldStartTime = sp.getString(Common.SP_VERSION_UPDATE_TIME,"2008-08-08 18:28:28");
		int days = 0;
		try {
			Date nowDate1 = format.parse(nowTime);
			Date oldDate1 = format.parse(oldStartTime);
			days = StringUtil.differentDaysByMillisecond(oldDate1,nowDate1);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (days >= 1){
			//判断是否需要更新版本,获取服务器上的版本号
			getServiceVersion();
		}
	}

	private void initAllBanner() {
		allBanner.setmAdapter(new XBanner.XBannerAdapter() {
			@Override
			public void loadBanner(XBanner banner, Object model, View view, int position) {
				Glide.with(MainGuest1Activity.this).load(allImages.get(position)).into((ImageView) view);
			}});
		allBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
			@Override
			public void onItemClick(XBanner banner, int position) {
					ProjectMessage projectMessage =
							projectMessageList.get(position);
					DataLargeHolder.getInstance()
							.save(projectMessage.getId(), projectMessage);
					Intent intent = new
							Intent(MainGuest1Activity.this,
							ProjectMessageDetailActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("id", projectMessage.getId());
					intent.putExtras(bundle);
					startActivity(intent);

			}
		});

	}

	private void initRecommendBanner() {
		xRecommendBanner.setmAdapter(new XBanner.XBannerAdapter() {
			@Override
			public void loadBanner(XBanner banner, Object model, View view, int position) {
				Glide.with(MainGuest1Activity.this).load(recommendImages.get(position)).into((ImageView) view);
			}});
		xRecommendBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
			@Override
			public void onItemClick(XBanner banner, int position) {
				if (hotProjectMessageList.size()>0) {
					ProjectMessage projectMessage =
							hotProjectMessageList.get(position);
					DataLargeHolder.getInstance()
							.save(projectMessage.getId(), projectMessage);
					Intent intent = new
							Intent(MainGuest1Activity.this,
							ProjectMessageDetailActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("id", projectMessage.getId());
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});
	}

	private void initMyClubBanner(){
		myClubBanner.setmAdapter(new XBanner.XBannerAdapter() {
			@Override
			public void loadBanner(XBanner banner, Object model, View view, int position) {
				Glide.with(MainGuest1Activity.this).load(myClubImages.get(position)).into((ImageView) view);
			}});
		myClubBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
			@Override
			public void onItemClick(XBanner banner, int position) {
				Club club = myClubList.get(position);
				Intent intent = new Intent(MainGuest1Activity.this,ClubActivityAllListActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("id",club.getId());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	private void getServiceVersion() {
		VersionBusiness versionBusiness = new VersionBusiness(context);
		versionBusiness.setHandler(this);
		versionBusiness.getVersion();
	}


	private void getMyClubList() {
		MyClubListBusiness myClubListBusiness = new MyClubListBusiness(context);
		myClubListBusiness.setHandler(this);
		myClubListBusiness.myClubList();

	}
	private void getProjectMessage() {
		int tag = 0;
		ProjectMessageListBusiness projectMessageListBusiness = new ProjectMessageListBusiness(context, pageNo, pageSize,tag);
		projectMessageListBusiness.setHandler(this);
		projectMessageListBusiness.getProjectMessageAllList();

	}
	//推荐活动活动
	private void getHotProjectMessage() {
		int tag = 0;
		ProjectMessageListBusiness projectMessageListBusiness = new ProjectMessageListBusiness(context, pageNo, pageSize,tag);
		projectMessageListBusiness.setHandler(this);
		projectMessageListBusiness.getHotProjectMessage();

	}
	//这个是菜单、信息、签到、编辑、更多信息点击事件
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_menu:
			showPopupMenu();
			break;
			case R.id.top_tv_login:
				startIntent(LoginActivity.class);
				break;
			case R.id.top_tv_exit:
				Intent intent = new
						Intent(this,WelcomActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|
						Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				break;
			case R.id.tv_login:
				startIntent(LoginActivity.class);
				break;
			case R.id.tv_register:
				startIntent(RegisterActivity.class);
				break;
			case R.id.cmoreactivity:
				startIntent(ClubProjectListActivity.class);
				break;
			case R.id.moreactivity:
				Intent intent1 = new
						Intent(MainGuest1Activity.this,ProjectMessageManageActivity.class);
				intent1.putExtra("tab_id",3);
				startActivity(intent1);
				break;
			case R.id.rmoreactivity:
				Intent intent2 = new
						Intent(MainGuest1Activity.this,ProjectMessageManageActivity.class);
				intent2.putExtra("tab_id",2);
				startActivity(intent2);
				break;
			default:
				break;
		}
	}

	//菜单按钮中的各个子页面点击事件
	private void showPopupMenu() {
		//更新popupwindow的状态
		window.update();
		//以下拉的方式显示，并且可以设置显示的位置
		window.showAsDropDown(iv_menu, 0, 0);

		lsvMore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
				menuListAdapter.setCurrentItem(position);
				menuListAdapter.setClick(true);
				menuListAdapter.notifyDataSetChanged();
				switch (position) {
					case 0:
						startIntent(LoginActivity.class);
						break;
					case 1:
						startIntent(LoginActivity.class);
						break;
					case 2:
						startIntent(LoginActivity.class);
						break;
					case 3:
						startIntent(LoginActivity.class);
						break;
					case 4:
						startIntent(LoginActivity.class);
						break;
					case 5:
						startIntent(LoginActivity.class);
						break;
					case 6:
						startIntent(LoginActivity.class);
						break;
					case 7:
						startIntent(LoginActivity.class);
						break;
					case 8:
						startIntent(LoginActivity.class);
						break;
					case 9:
						startIntent(LoginActivity.class);
						break;
					case 10:
						startIntent(LoginActivity.class);
						break;
				}
			}
		});
    }
	@Override
	public void onSuccess(ClubListResultBean clubListResultBean) {
		myClubImages.clear();
		myClubTitleList.clear();
		myClubList.clear();

		myClubList = clubListResultBean.getData();
		if (myClubList.size() != 0){
			for (int i=0;i<myClubList.size();i++){
				String pictureUrl = Common.URL_IMG_BASE+myClubList.get(i).getPicture();
				myClubImages.add(pictureUrl);
				myClubTitleList.add(myClubList.get(i).getName());
			}
			myClubBanner.setData(myClubImages,myClubTitleList);
		}


	}

	@Override
	public void onSuccess(ProjectMessageBean bean) {
		allImages.clear();
		allTitleList.clear();
		projectMessageList.clear();

		projectMessageList = bean.getList();
		if (projectMessageList.size() != 0){
			for (int i=0;i<projectMessageList.size();i++){
				String pictureUrl = Common.URL_IMG_BASE+projectMessageList.get(i).getPicture();
				allImages.add(pictureUrl);
				allTitleList.add(projectMessageList.get(i).getTitle());
			}
			allBanner.setData(allImages,allTitleList);
		}
	}

	@Override
	public void onHotSuccess(ProjectMessageBean bean) {
		recommendImages.clear();
		hotProjectMessageList.clear();
		recommendTitleList.clear();
		hotProjectMessageList = bean.getList();
		if (hotProjectMessageList.size() != 0){
			for (int i=0;i<hotProjectMessageList.size();i++){
				String pictureUrl = Common.URL_IMG_BASE+hotProjectMessageList.get(i).getPicture();
				recommendImages.add(pictureUrl);
				recommendTitleList.add(hotProjectMessageList.get(i).getTitle());
		}
			xRecommendBanner.setData(recommendImages,recommendTitleList);
		}

	}

	//重写返回键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
			ActivityCollector.finishAll();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onSuccess(int sVersionCode) {

		//获取手机版本号
		PackageManager pm = getPackageManager();
		try {
			PackageInfo pInfo = pm.getPackageInfo(getPackageName(),0);
			currVersion = pInfo.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		if (sVersionCode > currVersion){
			showUpdateDialog();
		}
	}

	public void showUpdateDialog() {
		// 构造对话框
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("软件更新");
		builder.setMessage("有新版本,建议更新!");
		// 更新
		builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse(Common.URL_APK);
				intent.setData(content_url);
				startActivity(intent);

				/*downloadApk();
				progressDialog.show();*/
				dialog.dismiss();
			}


		});
		// 稍后更新
		builder.setNegativeButton("稍后更新", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				Date startDate = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String startTime = format.format(startDate);
				sp.edit().putString(Common.SP_VERSION_UPDATE_TIME,startTime).commit();



				dialog.dismiss();
			}
		});
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}


	private void downloadApk() {
		ApkBusiness apkBusiness = new ApkBusiness(context);
		apkBusiness.setHandler(this);
		apkBusiness.getApkFile();
	}

	@Override
	public void onApkSuccess(File file) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		startActivity(intent);
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	@Override
	public void onApkFailed(String s) {
		showToast(s);
	}


	@Override
	public void onProgress(final long currentBytes, long totalBytes) {

		int total = Integer.parseInt(String.valueOf(totalBytes/1024));
		int current = Integer.parseInt(String.valueOf(currentBytes/1024));

		progressDialog.setMax(total);

		message = Message.obtain();
		message.what = 1000;
		message.arg1 = current;
		mHandler.sendMessage(message);

	}

	//弹出对话框
	public void showDialog(String s){
		//注册成功后的业务逻辑
		AlertDialog.Builder builder = new AlertDialog.Builder(MainGuest1Activity.this);
		builder.setTitle("自助实名认证");
		builder.setMessage(s);
		builder.setPositiveButton("实名认证", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//完成业务逻辑
				Intent intent = new
						Intent(MainGuest1Activity.this,
						CertificationActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("phone",phone);
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		});
		builder.show();

	}


}

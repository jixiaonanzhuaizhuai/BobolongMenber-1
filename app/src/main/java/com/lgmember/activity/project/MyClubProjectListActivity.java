package com.lgmember.activity.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.R;
import com.lgmember.adapter.ClubListAdapter;
import com.lgmember.bean.ClubListResultBean;
import com.lgmember.business.project.ClubListBusiness;
import com.lgmember.business.project.JoinClubBusiness;
import com.lgmember.business.project.MyClubListBusiness;
import com.lgmember.model.Club;
import com.lgmember.view.TopBarView;

import java.util.ArrayList;
import java.util.List;

import me.hwang.widgets.SmartPullableLayout;


public class MyClubProjectListActivity extends BaseActivity implements TopBarView.onTitleBarClickListener,ClubListAdapter.Callback,MyClubListBusiness.MyClubListResulHandler{

private LinearLayout ll_loading;
private ProgressBar progressBar;
private TextView loadDesc;
private ListView lv_club_list;
private int pageNo = 1;
private int pageSize = 5;
private int tag = 0;
private int total;
private boolean isLoading;
private String is_checked_in = "true";

private TopBarView topBar;


private List<Club> clubList;
private ClubListAdapter adapter;
private String TAG = "-CollectionActivity-";
private SmartPullableLayout mPullableLayout;
private static final int ON_REFRESH = 1;
private static final int ON_LOAD_MORE = 2;

private Handler mHandler = new Handler() {
@Override
public void handleMessage(android.os.Message msg) {
        switch (msg.what) {
        case ON_REFRESH:
        getInitData();
        adapter.notifyDataSetChanged();
        mPullableLayout.stopPullBehavior();
        break;
        case ON_LOAD_MORE:
        mPullableLayout.stopPullBehavior();
        break;
        }
        }
        };

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_club_list);
        init();
        }


@Override
protected void onResume() {
        super.onResume();
        getInitData();
        }

private void getInitData() {
        pageNo = 1;
        ll_loading.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        loadDesc.setText("正在拼命加载");
        getData();

        }

private void init() {
        topBar = (TopBarView)findViewById(R.id.topbar);
        topBar.setClickListener(this);
        lv_club_list =(ListView)findViewById(R.id.lv_club_list);
        ll_loading = (LinearLayout)findViewById(R.id.ll_loading);
        progressBar = (ProgressBar)findViewById(R.id.progressBar1);
        loadDesc = (TextView)findViewById(R.id.tv_loading_desc);
        clubList = new ArrayList<>();
        adapter = new ClubListAdapter(this,clubList,this,1);
        lv_club_list.setAdapter(adapter);
        lv_club_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Club club = clubList.get(position);
                Intent intent = new Intent(MyClubProjectListActivity.this,ClubActivityAllListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id",club.getId());
                intent.putExtras(bundle);
                startActivity(intent);
        }
        });

        mPullableLayout = (SmartPullableLayout) findViewById(R.id.layout_pullable);
        mPullableLayout.setOnPullListener(new SmartPullableLayout.OnPullListener() {
        @Override
        public void onPullDown() {
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                try {
                                        Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                                mHandler.sendEmptyMessage(ON_REFRESH);
                        }
                }).start();
        }

        @Override
        public void onPullUp() {
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                try {
                                        Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                                mHandler.sendEmptyMessage(ON_LOAD_MORE);
                        }
                }).start();
        }

                        });
                }

                private void getData() {
                        MyClubListBusiness myClubListBusiness = new MyClubListBusiness(context);
                        myClubListBusiness.setHandler(this);
                        myClubListBusiness.myClubList();
                }


                @Override
                public void onSuccess(ClubListResultBean bean) {
                        clubList.clear();
                        if (bean.getData() == null) {
                                progressBar.setVisibility(View.GONE);
                                loadDesc.setText("还没有数据");
                        } else {
                                ll_loading.setVisibility(View.GONE);
                                total = bean.getTotal();
                                clubList.addAll(bean.getData());
                                adapter.notifyDataSetChanged();
                                isLoading = false;
                        }
                }

                @Override
                public void onBackClick() {
                        finish();
                }

                @Override
                public void onRightClick() {

                }

        @Override
        public void click(View v) {
        }



}







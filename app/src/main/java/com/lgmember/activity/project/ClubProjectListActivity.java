package com.lgmember.activity.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
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
import com.lgmember.model.Club;
import com.lgmember.view.TopBarView;

import java.util.ArrayList;
import java.util.List;

import me.hwang.widgets.SmartPullableLayout;


public class ClubProjectListActivity extends BaseActivity implements ClubListBusiness.ClubListResulHandler,TopBarView.onTitleBarClickListener,ClubListAdapter.Callback ,JoinClubBusiness.JoinClubResulHandler{

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
        setContentView(R.layout.activity_club_list);
        init();
        }


@Override
protected void onResume() {
        super.onResume();
        getInitData();
        }

private void getInitData() {
        pageNo = 1;
        lv_club_list.setEnabled(false);
        clubList.clear();
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
        adapter = new ClubListAdapter(this,clubList,this);
        lv_club_list.setAdapter(adapter);
        lv_club_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Club club = clubList.get(position);
                Intent intent = new Intent(ClubProjectListActivity.this,ClubActivityAllListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id",club.getId());
                intent.putExtras(bundle);
                startActivity(intent);
        }
        });
        lv_club_list.setOnScrollListener(new AbsListView.OnScrollListener() {
                //滑动状态改变的时候，回调
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                //在滑动的时候不断的回调
                @Override
                public void onScroll(AbsListView view, int firstVisibleItem,
                                     int visibleItemCount, int totalItemCount) {
                        if (firstVisibleItem+visibleItemCount==totalItemCount&&!isLoading) {
                                isLoading = true;
                                if (totalItemCount< total){
                                        pageNo++;
                                        getData();
                                }
                        }
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
                String search = "";
                ClubListBusiness clubListBusiness = new ClubListBusiness(context, pageNo, pageSize, search);
                clubListBusiness.setHandler(this);
                clubListBusiness.getClubList();
        }


        @Override
        public void onSuccess(ClubListResultBean bean) {
                lv_club_list.setEnabled(true);
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
                int club_id = clubList.get(Integer.parseInt(v.getTag().toString())).getId();
                joinClub(club_id);
        }

        private void joinClub(int club_id) {
                JoinClubBusiness joinClubBusiness = new JoinClubBusiness(context, club_id);
                joinClubBusiness.setHandler(this);
                joinClubBusiness.joinClub();
        }

        @Override
        public void onSuccess() {
                getInitData();
        }
}







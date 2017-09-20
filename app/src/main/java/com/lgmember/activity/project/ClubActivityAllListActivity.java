package com.lgmember.activity.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.R;
import com.lgmember.adapter.ProjectMessageListAdapter;
import com.lgmember.bean.ProjectMessageBean;
import com.lgmember.business.project.ClubActivityListBusiness;
import com.lgmember.model.ProjectMessage;
import com.lgmember.util.DataLargeHolder;
import com.lgmember.view.TopBarView;

import java.util.ArrayList;
import java.util.List;

import me.hwang.widgets.SmartPullableLayout;


public class ClubActivityAllListActivity extends BaseActivity implements TopBarView.onTitleBarClickListener,ClubActivityListBusiness.ClubActivityListResulHandler {

    private LinearLayout ll_loading;
    private ProgressBar progressBar;
    private TextView loadDesc;
    private ListView lv_all_list;
    private int pageNo = 1;
    private int pageSize = 5;
    private int total;
    private int tagNum = 0;
    private int club_id;
    private boolean isLoading;
    private TopBarView topBar;

    private List<ProjectMessage> projectMessageList ;
    private ProjectMessageListAdapter adapter;
    private String TAG = "-FragmentActivityList-";

    private SmartPullableLayout mPullableLayout;

    private static final int ON_REFRESH = 1;
    private static final int ON_LOAD_MORE = 2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ON_REFRESH:
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
        setContentView(R.layout.activity_club_activity_list);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        getInitData();
    }

    private void getInitData() {
        pageNo = 1;
        projectMessageList.clear();
        lv_all_list.setEnabled(false);
        ll_loading.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        loadDesc.setText("正在拼命加载");
        getData();
    }

    private void init() {
        Bundle bundle = this.getIntent().getExtras();
        club_id = bundle.getInt("id");
        topBar = (TopBarView)findViewById(R.id.topbar);
        topBar.setClickListener(this);
        lv_all_list=(ListView)findViewById(R.id.lv_all_activity_list);
        projectMessageList = new ArrayList<>();
        adapter = new ProjectMessageListAdapter(this,projectMessageList);
        lv_all_list.setAdapter(adapter);
        lv_all_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProjectMessage projectMessage =
                        projectMessageList.get(position);
                DataLargeHolder.getInstance()
                        .save(projectMessage.getId(),projectMessage);
                Intent intent = new Intent(ClubActivityAllListActivity.this,ClubProjectMessageDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id",projectMessage.getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        lv_all_list.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        ll_loading = (LinearLayout)findViewById(R.id.ll_loading);
        progressBar = (ProgressBar)findViewById(R.id.progressBar1);
        loadDesc = (TextView)findViewById(R.id.tv_loading_desc);
        mPullableLayout = (SmartPullableLayout)findViewById(R.id.layout_pullable);
        mPullableLayout.setOnPullListener(new SmartPullableLayout.OnPullListener() {
            @Override
            public void onPullDown() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            pageNo = 1;
                            projectMessageList.clear();
                            getData();
                            Thread.sleep(3000);
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
                            getInitData();
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
        int tag = 0;
        String search = "";
        ClubActivityListBusiness clubActivityListBusiness = new
                ClubActivityListBusiness(this,pageNo,pageSize,search,tag,club_id);
        clubActivityListBusiness.setHandler(this);
        clubActivityListBusiness.getClubActivityList();
    }
    @Override
    public void onSuccess(ProjectMessageBean bean) {
        total = bean.getTotal();
        if (bean.getList().size() == 0){
            lv_all_list.setEnabled(false);
            progressBar.setVisibility(View.GONE);
            loadDesc.setText("还没有数据");
        }else {
            lv_all_list.setEnabled(true);
            ll_loading.setVisibility(View.GONE);
            projectMessageList.addAll(bean.getList());
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

}





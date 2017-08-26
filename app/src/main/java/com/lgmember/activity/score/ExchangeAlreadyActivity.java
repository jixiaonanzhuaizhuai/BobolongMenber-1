package com.lgmember.activity.score;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lgmember.activity.BaseFragment;
import com.lgmember.activity.MainActivity;
import com.lgmember.activity.R;
import com.lgmember.adapter.ExchangeGiftAdapter;
import com.lgmember.adapter.ExchangeSendGiftAdapter;
import com.lgmember.bean.ExchangeGiftResultBean;
import com.lgmember.bean.ExchangeSendGiftResultBean;
import com.lgmember.business.score.ExchangeAllGiftBusiness;
import com.lgmember.model.Gift;
import com.lgmember.model.GiftSend;
import com.lgmember.view.TopBarView;

import java.util.ArrayList;
import java.util.List;

import me.hwang.widgets.SmartPullableLayout;

/**
 * Created by Yanan_Wu on 2016/12/19.
 */

public class ExchangeAlreadyActivity extends BaseFragment implements ExchangeAllGiftBusiness.ExchangeSendGiftHandler,TopBarView.onTitleBarClickListener{

    private GridView gridView;
    private LinearLayout ll_loading;
    private ProgressBar progressBar;
    private TextView loadDesc;

    private ExchangeSendGiftAdapter adapter;
    private List<GiftSend> giftSendsList;

    private TopBarView topBar;

    private int pageNo = 1;
    private int pageSize = 20;
    private int total;
    private boolean isLoading;
    private SmartPullableLayout mPullableLayout;
    private static final int ON_REFRESH = 1;
    private static final int ON_LOAD_MORE = 2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_exchangeall, container, false);
        init(view);
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getInitData();
    }

    private void  getInitData(){
        pageNo = 1;
        ll_loading.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        loadDesc.setText("正在拼命加载");
        fillData();
    }

    private void init(View view) {

        topBar = (TopBarView)view.findViewById(R.id.topbar);
        topBar.setClickListener(this);

        gridView = (GridView)view.findViewById(R.id.mygridview);
        giftSendsList = new ArrayList<>();
        adapter = new ExchangeSendGiftAdapter(getActivity(),giftSendsList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),ExchangeAlreadyGiftDetail.class);
//                intent.putExtra("flag",false);
                intent.putExtra("giftSend",new Gson().toJson(giftSendsList.get(position)));
                startActivity(intent);

            }
        });

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            //滑动状态改变的时候，回调
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
            //在滑动的时候不断的回调
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem+visibleItemCount==totalItemCount&&!isLoading) {
                    isLoading = true;
                    if (totalItemCount< total){
                        pageNo++;
                        fillData();
                    }
                }
            }
        });

        ll_loading = (LinearLayout)view.findViewById(R.id.ll_loading);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar1);
        loadDesc = (TextView)view.findViewById(R.id.tv_loading_desc);
        mPullableLayout = (SmartPullableLayout)view.findViewById(R.id.layout_pullable);
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

    private void fillData(){
        ExchangeAllGiftBusiness exchangeAllGiftBusiness = new ExchangeAllGiftBusiness(getActivity(),pageNo,pageSize);
        exchangeAllGiftBusiness.setHandler(this);
        exchangeAllGiftBusiness.getAlreadyGift();
    }


    @Override
    public void onSuccess(ExchangeSendGiftResultBean bean) {

        giftSendsList.clear();

        total = bean.getTotal();
        if (total == 0){
            progressBar.setVisibility(View.GONE);
            loadDesc.setText("当前还没有数据");
        }else {
            ll_loading.setVisibility(View.GONE);
            giftSendsList.addAll(bean.getData());
            adapter.notifyDataSetChanged();
            isLoading = false;
        }

    }

    @Override
    public void onBackClick() {
        Intent intent = new Intent(getActivity(),MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRightClick() {

    }
}
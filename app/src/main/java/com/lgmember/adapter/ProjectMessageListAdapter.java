package com.lgmember.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lgmember.activity.R;
import com.lgmember.activity.project.ProjectMessageDetailActivity;
import com.lgmember.model.Project;
import com.lgmember.model.ProjectMessage;
import com.lgmember.util.Common;
import com.lgmember.util.StringUtil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Yanan_Wu on 2017/2/14.
 */

public class ProjectMessageListAdapter extends BaseAdapter {

    private List<ProjectMessage> projectsMessageList;
    private Context context;
    private LayoutInflater layoutInflater;
    private int mCurrentItem=0;
    private boolean isClick=false;

    public ProjectMessageListAdapter(Context context, List<ProjectMessage> projectsMessageList){
        this.context = context;
        this.projectsMessageList = projectsMessageList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return projectsMessageList.size();
    }

    //获得某一位置的数据
    @Override
    public Object getItem(int position) {
        return projectsMessageList.get(position) ;
    }

    //获得唯一标识
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder vh;
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.activity_activitylistitem, null);
            ImageView iv_img = (ImageView)view.findViewById(R.id.iv_img);
            TextView title = (TextView) view.findViewById(R.id.txt_title);
            TextView create_time = (TextView) view.findViewById(R.id.txt_create_time);
            TextView state = (TextView)view.findViewById(R.id.txt_state);
            TextView checkin_end_time =(TextView)view.findViewById(R.id.txt_checkin_end_time);
            TextView create_time_1 = (TextView) view.findViewById(R.id.txt_create_time_1);
            TextView state_1 = (TextView)view.findViewById(R.id.txt_state_1);
            TextView checkin_end_time_1 =(TextView)view.findViewById(R.id.txt_checkin_end_time_1);

            //打包
            vh = new ViewHolder();
            vh.iv_img = iv_img;
            vh.title = title;
            vh.create_time = create_time;
            vh.state = state;
            vh.checkin_end_time = checkin_end_time;
            vh.create_time_1 = create_time_1;
            vh.state_1 = state_1;
            vh.checkin_end_time_1 = checkin_end_time_1;
            //上身
            view.setTag(vh);
        } else {
            view = convertView;
            vh = (ViewHolder) view.getTag();
        }

        ProjectMessage projectMessage = projectsMessageList.get(position);

        String str = projectMessage.getPicture();
        if (str == null){
            vh.iv_img.setImageResource(R.drawable.touxiang);
        }else {
            String path = Common.URL_IMG_BASE+str;
            Glide.with(context).load(path).placeholder(R.mipmap.defaul_background_img).into(vh.iv_img);
        }
        if (mCurrentItem==position&&isClick){
            view.setBackgroundResource(R.color.main_2);
            vh.title.setTextColor(Color.WHITE);
            vh.create_time.setTextColor(Color.WHITE);
            vh.checkin_end_time.setTextColor(Color.WHITE);
            vh.state.setTextColor(Color.WHITE);
            vh.create_time_1.setTextColor(Color.WHITE);
            vh.checkin_end_time_1.setTextColor(Color.WHITE);
            vh.state_1.setTextColor(Color.WHITE);
        }else {
            view.setBackgroundResource(R.color.white);
            vh.title.setTextColor(context.getResources().getColor(R.color.main_2));
            vh.create_time.setTextColor(context.getResources().getColor(R.color.main_2));
            vh.checkin_end_time.setTextColor(context.getResources().getColor(R.color.main_2));
            vh.state.setTextColor(context.getResources().getColor(R.color.main_2));
            vh.create_time_1.setTextColor(context.getResources().getColor(R.color.main_2));
            vh.checkin_end_time_1.setTextColor(context.getResources().getColor(R.color.main_2));
            vh.state_1.setTextColor(context.getResources().getColor(R.color.main_2));
        }

        vh.title.setText(""+projectMessage.getTitle());
        vh.create_time.setText(""+projectMessage.getStart_time());
        vh.state.setText(""+StringUtil.numToJoinState(projectMessage.getState()));
        vh.checkin_end_time.setText(""+StringUtil.subString(projectMessage.getCheckin_end_time()));

        return view;
    }

    public void setCurrentItem(int currentItem){
        this.mCurrentItem=currentItem;
    }

    public void setClick(boolean click){
        this.isClick=click;
    }

    private class ViewHolder {
        public ImageView iv_img;
        public TextView title;
        public TextView create_time;
        public TextView state;
        public TextView checkin_end_time;
        public TextView create_time_1;
        public TextView state_1;
        public TextView checkin_end_time_1;

    }
}


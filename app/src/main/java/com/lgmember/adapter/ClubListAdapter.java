package com.lgmember.adapter;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lgmember.activity.R;
import com.lgmember.model.Card;
import com.lgmember.model.Club;
import com.lgmember.model.ProjectMessage;
import com.lgmember.util.Common;
import com.lgmember.util.StringUtil;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/2/14.
 */

public class ClubListAdapter extends BaseAdapter implements View.OnClickListener{

    private List<Club> clubList;
    private Context context;
    private Callback mCallback;
    private LayoutInflater layoutInflater;

    public ClubListAdapter(Context context, List<Club> clubList, Callback callback) {
        this.context = context;
        this.clubList = clubList;
        this.mCallback = callback;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public interface Callback{
        public void click(View v);
    }

    @Override
    public int getCount() {
        return clubList.size();
    }

    //获得某一位置的数据
    @Override
    public Object getItem(int position) {
        return clubList.get(position);
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
            view = layoutInflater.inflate(R.layout.activity_club_list_item, null);
            TextView name = (TextView) view.findViewById(R.id.tv_name);
            TextView limit_num = (TextView) view.findViewById(R.id.tv_limit_num);
            TextView dep = (TextView) view.findViewById(R.id.tv_dep);
            TextView create_time = (TextView) view.findViewById(R.id.tv_create_time);
            TextView description = (TextView) view.findViewById(R.id.tv_description);
            Button added = (Button)view.findViewById(R.id.btn_added);

            //打包
            vh = new ViewHolder();
            vh.name = name;
            vh.limit_num = limit_num;
            vh.dep = dep;
            vh.create_time = create_time;
            vh.description = description;
            vh.added = added;
            //上身
            view.setTag(vh);
        } else {
            view = convertView;
            vh = (ViewHolder) view.getTag();
        }

        Club club = clubList.get(position);

        vh.name .setText(""+club.getName());
        vh.limit_num.setText(""+club.getLimit_num());
        vh.dep.setText(""+club.getDep());
        vh.create_time.setText(""+club.getCreate_time());
        vh.description.setText(""+club.getDescription());
        if (club.isAdded()){
            vh.added.setText("已加入");
            vh.added.setEnabled(false);
        }else {
            vh.added.setText("加入");
            vh.added.setOnClickListener(this);
            vh.added.setTag(position);
        }


        return view;
    }

    @Override
    public void onClick(View v) {
        mCallback.click(v);

    }

    private class ViewHolder {
        public TextView name;
        public TextView limit_num;
        public TextView dep;
        public TextView create_time;
        public TextView description;
        public Button added;

    }

}

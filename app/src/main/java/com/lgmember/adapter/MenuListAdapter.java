package com.lgmember.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lgmember.activity.R;
import com.lgmember.model.Menu;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/10/5.
 */

public class MenuListAdapter extends BaseAdapter {

        private List<Menu> menuList;
        private Context context;
        private int mCurrentItem= -1 ;
        private boolean isClick=false;
        private LayoutInflater layoutInflater;
        private int[] menuImgDatas_p = {R.mipmap.shouye01_w,R.mipmap.shouye02_w,R.mipmap.shouye03_w,R.mipmap.shouye04_w,R.mipmap.shouye05_w,R.mipmap.shouye06_w,R.mipmap.shouye07_w,R.mipmap.shouye08_w,R.mipmap.shouye09_w,R.mipmap.shouye10_w,R.mipmap.shouye11_w};

    /*
    *
    * 自定义接口，用于回调按钮点击事件到Activity*/

        public MenuListAdapter(Context context, List<Menu> menuList){
            this.context = context;
            this.menuList = menuList;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return menuList.size();
        }

        //获得某一位置的数据
        @Override
        public Object getItem(int position) {
            return menuList.get(position) ;
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
                view = layoutInflater.inflate(R.layout.activity_menu_list_item, null);

                LinearLayout ll = (LinearLayout)view.findViewById(R.id.ll);
                ImageView menu_img = (ImageView)view.findViewById(R.id.iv_menu);
                TextView menu_title = (TextView) view.findViewById(R.id.txt_menu);
                // Button card_state = (Button)view.findViewById(R.id.btn_card_state);

                //打包
                vh = new ViewHolder();
                vh.ll = ll;
                vh.menu_img = menu_img;
                vh.menu_title = menu_title;

                //  vh.card_state = card_state;

                //上身
                view.setTag(vh);
            } else {
                view = convertView;
                vh = (ViewHolder) view.getTag();
            }
            Menu menu = menuList.get(position);
            if (position == mCurrentItem){
                vh.menu_img.setImageResource(menuImgDatas_p[position]);
                vh.ll.setBackgroundColor(context.getResources().getColor(R.color.main_2));
                vh.menu_title.setTextColor(Color.WHITE);
            }else {
                vh.menu_img.setImageResource(menu.getMenuImg());
                vh.ll.setBackgroundColor(Color.WHITE);
                vh.menu_title.setTextColor(context.getResources().getColor(R.color.main_2));
            }
            vh.menu_title.setText("" + menu.getMenuName());


            return view;
        }

    public void setCurrentItem(int currentItem){
        this.mCurrentItem=currentItem;
    }

    public void setClick(boolean click){
        this.isClick=click;
    }

        private class ViewHolder {
            private LinearLayout ll;
            public ImageView menu_img;
            public TextView menu_title;

        }
    }



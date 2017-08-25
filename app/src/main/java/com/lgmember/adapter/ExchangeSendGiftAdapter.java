package com.lgmember.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lgmember.activity.R;
import com.lgmember.model.Gift;
import com.lgmember.model.GiftSend;
import com.lgmember.util.Common;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/2/14.
 */

public class ExchangeSendGiftAdapter extends BaseAdapter {

    private List<GiftSend> giftSendsList;
    private Context context;
    private LayoutInflater layoutInflater;

    public ExchangeSendGiftAdapter(Context context, List<GiftSend> giftSendsList){
        this.context = context;
        this.giftSendsList = giftSendsList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return giftSendsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder vh;
        if (convertView == null){
            view = layoutInflater.inflate(R.layout.grid_item,null);
            final ImageView giftImg = (ImageView)view.findViewById(R.id.iv_gift_img);
            TextView giftName = (TextView)view.findViewById(R.id.tv_gift_name);
            //打包
            vh = new ViewHolder();
            vh.giftImg = giftImg;
            vh.giftName = giftName;
            //上身
            view.setTag(vh);
        }else {
            view = convertView;
            vh = (ViewHolder)view.getTag();
        }
        final GiftSend giftSend = giftSendsList.get(position);
        String imgPath = Common.URL_IMG_BASE + giftSend.getPicture();
        Glide.with(context).load(imgPath).placeholder(R.mipmap.defaul_background_img).into(vh.giftImg);

        vh.giftName.setText(giftSend.getGift_name());
        return view;
    }

     private class ViewHolder{
        public ImageView giftImg;
        public TextView giftName;
}
}


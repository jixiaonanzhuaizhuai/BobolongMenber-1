package com.lgmember.bean;

import com.lgmember.model.Gift;
import com.lgmember.model.GiftSend;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/4/13.
 */

public class ExchangeSendGiftResultBean extends HttpResultBean {

    private int total;
    private List<GiftSend> data ;



    public ExchangeSendGiftResultBean(int code, int total, List<GiftSend> data) {
        super(code);
        this.total = total;
        this.data = data;
    }

    public ExchangeSendGiftResultBean(){
        super();
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<GiftSend> getData() {
        return data;
    }

    public void setData(List<GiftSend> data) {
        this.data = data;
    }
}

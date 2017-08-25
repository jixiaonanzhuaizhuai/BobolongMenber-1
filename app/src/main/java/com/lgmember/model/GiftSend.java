package com.lgmember.model;

/**
 * Created by Yanan_Wu on 2017/8/25.
 */
/*
*

 * @param gift_send_id   礼物发送id

 * @param gift_id        礼物id

 * @param gift_name      礼物名称

 * @param receive_name   收件人名字

 * @param receive_mobile 收件人电话

 * @param receive_addr   收件地址

 * @param create_time    兑换时间

 * @param postcode       邮寄编号

*/


public class GiftSend {
    private int gift_send_id;
    private int gift_id;
    private String gift_name;
    private String receive_name;
    private String receive_mobile;
    private String receive_addr;
    private String create_time;
    private String postcode;
    private String picture;



    public int getGift_send_id() {
        return gift_send_id;
    }

    public void setGift_send_id(int gift_send_id) {
        this.gift_send_id = gift_send_id;
    }

    public int getGift_id() {
        return gift_id;
    }

    public void setGift_id(int gift_id) {
        this.gift_id = gift_id;
    }

    public String getGift_name() {
        return gift_name;
    }

    public void setGift_name(String gift_name) {
        this.gift_name = gift_name;
    }

    public String getReceive_name() {
        return receive_name;
    }

    public void setReceive_name(String receive_name) {
        this.receive_name = receive_name;
    }

    public String getReceive_mobile() {
        return receive_mobile;
    }

    public void setReceive_mobile(String receive_mobile) {
        this.receive_mobile = receive_mobile;
    }

    public String getReceive_addr() {
        return receive_addr;
    }

    public void setReceive_addr(String receive_addr) {
        this.receive_addr = receive_addr;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}

package com.lgmember.model;

/**
 * Created by Yanan_Wu on 2017/9/13.
 */

/*
*
 * 俱乐部
 *
 * @param id          俱乐部id
 * @param name        名称
 * @param limit_num   限制人数
 * @param dep         部门
 * @param description 介绍
 * @param create_time 创建时间
 * @param added       是否已经加入
*/

public class Club {
    private int id;
    private String name;
    private int limit_num;
    private String dep;
    private String description;
    private String create_time;
    private int state;
    private boolean added;
    private String picture;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLimit_num() {
        return limit_num;
    }

    public void setLimit_num(int limit_num) {
        this.limit_num = limit_num;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }
}

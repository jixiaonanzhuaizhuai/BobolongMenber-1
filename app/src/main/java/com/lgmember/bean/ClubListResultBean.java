package com.lgmember.bean;

import com.lgmember.model.Club;
import com.lgmember.model.ProjectMessage;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/3/9.
 */

public class ClubListResultBean extends HttpResultBean {
    private int total;
    private List<Club> data;

    public List<Club> getData() {
        return data;
    }

    public void setData(List<Club> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ClubListResultBean(){
        super();
    }

    public ClubListResultBean(int total, List<Club> data) {
        this.total = total;
        this.data = data;
    }

    public ClubListResultBean(int code, int total, List<Club> data) {
        super(code);
        this.total = total;
        this.data = data;
    }
}

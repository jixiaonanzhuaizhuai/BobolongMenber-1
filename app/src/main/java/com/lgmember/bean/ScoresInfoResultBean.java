package com.lgmember.bean;

import com.lgmember.model.Project;
import com.lgmember.model.ScoresInfo;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/3/9.
 */

public class ScoresInfoResultBean extends HttpResultBean {
    private ScoresInfo data;

    public ScoresInfoResultBean(){
        super();
    }

    public ScoresInfoResultBean( ScoresInfo data) {
        this.data = data;
    }

    public ScoresInfoResultBean(int code, ScoresInfo data) {
        super(code);
        this.data = data;
    }

    public ScoresInfo getData() {
        return data;
    }

    public void setData(ScoresInfo data) {
        this.data = data;
    }



}

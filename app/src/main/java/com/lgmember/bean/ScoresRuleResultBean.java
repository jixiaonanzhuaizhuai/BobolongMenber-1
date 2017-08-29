package com.lgmember.bean;

import com.lgmember.model.Message;
import com.lgmember.model.ScoresRule;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/3/9.
 */

public class ScoresRuleResultBean extends HttpResultBean {
    private ScoresRule data;
    public ScoresRule getData() {
        return data;
    }

    public void setData(ScoresRule data) {
        this.data = data;
    }
}

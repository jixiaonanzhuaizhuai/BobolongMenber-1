package com.lgmember.business.score;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.MessageBean;
import com.lgmember.impl.ScoresImpl;
import com.lgmember.model.ScoresInfo;
import com.lgmember.model.ScoresRule;

import org.json.JSONObject;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class ScoresRuleBusiness {
    private Context context;

    public ScoresRuleBusiness(Context context) {
        super();
        this.context = context;
    }

    public  void getScoresRule(){
        ScoresImpl scoresRule = new ScoresImpl();
        scoresRule.getScoresRule(handler,context);
    }


    private ScoresRuleHandler handler;
    public interface ScoresRuleHandler extends HttpHandler {
        //当参数为空
        public void onSuccess(ScoresRule scoresRule);
    }
    public void setHandler(ScoresRuleHandler handler){
        this.handler = handler;
    }


    }
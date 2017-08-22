package com.lgmember.business.score;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.ScoresImpl;
import com.lgmember.model.ScoresInfo;

import org.json.JSONObject;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class ScoresInfoBusiness {
    private Context context;

    public ScoresInfoBusiness(Context context) {
        super();
        this.context = context;
    }

    public  void getScoresInfo(){
        ScoresImpl scoresRule = new ScoresImpl();
        scoresRule.getScoresInfo(handlerInfo,context);
    }

    private ScoresInfoHandler handlerInfo;
    public interface ScoresInfoHandler extends HttpHandler {
        //当参数为空
        public void onSuccess(ScoresInfo scoresInfo);
    }
    public void setHandler(ScoresInfoHandler handlerInfo){
        this.handlerInfo = handlerInfo;
    }

    }
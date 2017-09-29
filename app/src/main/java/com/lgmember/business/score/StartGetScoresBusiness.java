package com.lgmember.business.score;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.ScoresImpl;
import com.lgmember.model.ScoresRule;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class StartGetScoresBusiness {
    private Context context;

    public StartGetScoresBusiness(Context context) {
        super();
        this.context = context;
    }

    public  void startGetScores(){
        ScoresImpl scoresRule = new ScoresImpl();
        scoresRule.startGetScores(handler,context);
    }

    private StartGetScoresHandler handler;
    public interface StartGetScoresHandler extends HttpHandler {
        //当参数为空
        public void onSuccess();
    }
    public void setHandler(StartGetScoresHandler handler){
        this.handler = handler;
    }


    }
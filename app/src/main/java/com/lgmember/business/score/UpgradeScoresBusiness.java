package com.lgmember.business.score;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.ScoresImpl;
import com.lgmember.model.ScoresRule;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class UpgradeScoresBusiness {
    private Context context;
    private int level;
    private int point_before;
    private int point_after;

    public UpgradeScoresBusiness(Context context,int level,int point_before,int point_after) {
        super();
        this.context = context;
        this.level = level;
        this.point_before = point_before;
        this.point_after = point_after;
    }

    public  void upgradeScores(){
        ScoresImpl scoresRule = new ScoresImpl();
        scoresRule.upgradeScores(level,point_before,point_after,handler,context);
    }


    private UpgradeScoresHandler handler;
    public interface UpgradeScoresHandler extends HttpHandler {
        //当参数为空
        public void onSuccess();
    }
    public void setHandler(UpgradeScoresHandler handler){
        this.handler = handler;
    }


    }
package com.lgmember.business.project;

import android.content.Context;
import android.text.TextUtils;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.ProjectMessageImpl;

import org.json.JSONObject;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class JoinActivityBusiness {
    private int project_id;
    private Context context;
    private ProjectMessageImpl projectMessageImpl;

    public JoinActivityBusiness(Context context, int project_id) {
        super();
        this.context = context;
        this.project_id = project_id;
    }

    // 先验证参数的可发性，再登陆
    public void join() {
        // 判断活动码是否有效
        projectMessageImpl = new ProjectMessageImpl();
        projectMessageImpl.join(project_id,handler,context);
    }
    public void clubActivityJoin() {
        // 判断活动码是否有效
        projectMessageImpl = new ProjectMessageImpl();
        projectMessageImpl.clubActivityJoin(project_id,handler,context);
    }


    private JoinActivityResulHandler handler;

    public interface JoinActivityResulHandler extends HttpHandler {

        public void onSuccess(String string);

    }
    public void setHandler(JoinActivityResulHandler handler){
        this.handler = handler;
    }

    }
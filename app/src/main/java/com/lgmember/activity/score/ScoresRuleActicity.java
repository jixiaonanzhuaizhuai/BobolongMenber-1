package com.lgmember.activity.score;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.BaseFragment;
import com.lgmember.activity.R;
import com.lgmember.business.message.MemberMessageBusiness;
import com.lgmember.business.score.ScoresRuleBusiness;
import com.lgmember.business.score.UpgradeScoresBusiness;
import com.lgmember.model.Member;
import com.lgmember.model.ScoresRule;
import com.lgmember.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Yanan_Wu on 2016/12/19.
 */

public class ScoresRuleActicity extends BaseFragment implements ScoresRuleBusiness.ScoresRuleHandler ,View.OnClickListener ,MemberMessageBusiness.MemberMessageResulHandler,UpgradeScoresBusiness.UpgradeScoresHandler{

    private TextView rule1,rule2,rule3,rule4,rule5,rule6,rule7,rule8,rule9,rule10,rule11,rule12
            ,rule13,rule14,rule15,rule16,rule17,rule18,rule19,rule20,rule21,rule22,rule23,rule24,rule25,rule26
            ,rule27,rule28,rule29,rule30,rule31;
    private ScoresRule scoresReluLocal;
    private int authorized,level,point,point_after;
    private Button upgradeBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_scoresrule, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getScoresRuleRule();
        getMemberData();
    }

    public void initView(View view){
        rule1 = (TextView)view.findViewById(R.id.rule1);
        rule2 = (TextView)view.findViewById(R.id.rule2);
        rule3 = (TextView)view.findViewById(R.id.rule3);
        rule4 = (TextView)view.findViewById(R.id.rule4);
        rule5 = (TextView)view.findViewById(R.id.rule5);
        rule6 = (TextView)view.findViewById(R.id.rule6);
        rule7 = (TextView)view.findViewById(R.id.rule7);
        rule8 = (TextView)view.findViewById(R.id.rule8);
        rule9 = (TextView)view.findViewById(R.id.rule9);
        rule10 = (TextView)view.findViewById(R.id.rule10);
        rule11 = (TextView)view.findViewById(R.id.rule11);
        rule12 = (TextView)view.findViewById(R.id.rule12);
        rule13 = (TextView)view.findViewById(R.id.rule13);
        rule14 = (TextView)view.findViewById(R.id.rule14);
        rule15 = (TextView)view.findViewById(R.id.rule15);
        rule16 = (TextView)view.findViewById(R.id.rule16);
        rule17 = (TextView)view.findViewById(R.id.rule17);
        rule18 = (TextView)view.findViewById(R.id.rule18);
        rule19 = (TextView)view.findViewById(R.id.rule19);
        rule20 = (TextView)view.findViewById(R.id.rule20);
        rule21 = (TextView)view.findViewById(R.id.rule21);
        rule22 = (TextView)view.findViewById(R.id.rule22);
        rule23 = (TextView)view.findViewById(R.id.rule23);
        rule24 = (TextView)view.findViewById(R.id.rule24);
        rule25 = (TextView)view.findViewById(R.id.rule25);
        rule26 = (TextView)view.findViewById(R.id.rule26);
        rule27 = (TextView)view.findViewById(R.id.rule27);
        rule28 = (TextView)view.findViewById(R.id.rule28);
        rule29 = (TextView)view.findViewById(R.id.rule29);
        rule30 = (TextView)view.findViewById(R.id.rule30);
        rule31 = (TextView)view.findViewById(R.id.rule31);

        upgradeBtn = (Button)view.findViewById(R.id.upgradeBtn);
        upgradeBtn.setOnClickListener(this);
    }

    private void getMemberData() {
        MemberMessageBusiness memberMessage = new MemberMessageBusiness(getActivity());
        memberMessage.setHandler(this);
        memberMessage.getMemberMessage();
    }

    public void getScoresRuleRule(){
        ScoresRuleBusiness scoresRuleBusiness = new ScoresRuleBusiness(getActivity());
        //处理结果
        scoresRuleBusiness.setHandler(this);
        scoresRuleBusiness.getScoresRule();
    }

    private void upgradeScores() {
        UpgradeScoresBusiness upgradeScoresBusiness = new UpgradeScoresBusiness(getActivity(),level + 1,point,point_after);
        upgradeScoresBusiness.setHandler(this);
        upgradeScoresBusiness.upgradeScores();


    }
    @Override
    public void onSuccess(final ScoresRule scoresRule) {
            scoresReluLocal = scoresRule;
            rule1.setText(""+scoresRule.getRedup());
            rule2.setText(""+scoresRule.getRedcut());
            rule3.setText(""+scoresRule.getAgup());
            rule4.setText(""+scoresRule.getAgcut());
            rule5.setText(""+scoresRule.getAuup());
            rule6.setText(""+scoresRule.getAucut());
            rule7.setText(""+scoresRule.getAgdown());
            rule8.setText(""+scoresRule.getAudown());
            rule9.setText(""+scoresRule.getSign());
            rule10.setText(""+scoresRule.getRegister());
            rule11.setText(""+scoresRule.getPunish());
            rule12.setText(""+scoresRule.getComplete());
            rule13.setText(""+scoresRule.getShare());
            rule14.setText(""+scoresRule.getProject_A());
            rule15.setText(""+scoresRule.getProject_B());
            rule16.setText(""+scoresRule.getProject_C());
            rule17.setText(""+scoresRule.getProject_double());
            rule18.setText(""+scoresRule.getProgram());
            rule19.setText(""+scoresRule.getProgram_double());
            rule20.setText(""+scoresRule.getA_red_double());
            rule21.setText(""+scoresRule.getA_ag_double());
            rule22.setText(""+scoresRule.getA_au_double());
            rule23.setText(""+scoresRule.getA_au_double());
            rule24.setText(""+scoresRule.getB_red_double());
            rule25.setText(""+scoresRule.getB_ag_double());
            rule26.setText(""+scoresRule.getB_au_double());
            rule27.setText(""+scoresRule.getB_diamond_double());
            rule28.setText(""+scoresRule.getC_red_double());
            rule29.setText(""+scoresRule.getC_ag_double());
            rule30.setText(""+scoresRule.getC_au_double());
            rule31.setText(""+scoresRule.getC_diamond_double());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.upgradeBtn:
                if (authorized == 0){
                    showToast("当前会员未实名，无法升级！");
                    return;
                }
                if (level == 1 &&
                        point < scoresReluLocal.getRedup()){
                    showToast("当前积分不足，无法升级到银卡！");
                    return;
                }
                if (level == 2 &&
                        point < scoresReluLocal.getAgup()){
                    showToast("当前积分不足，无法升级到金卡！");
                    return;
                }
                if (level == 3 &&
                        point < scoresReluLocal.getAuup()){
                    showToast("当前积分不足，无法升级到钻石卡！");
                    return;
                }
                if (level == 4 ){
                    showToast("目前为钻石卡(最高级别)，无法升级！");
                    return;
                }
                if (level == 1){
                    point_after = point - scoresReluLocal.getRedcut();
                }
                if (level == 2){
                    point_after = point - scoresReluLocal.getAgcut();
                }
                if (level == 3){
                    point_after = point - scoresReluLocal.getAucut();
                }
                upgradeScores();

                break;
        }
    }



    @Override
    public void onSuccess(Member member) {
        authorized = member.getAuthorized();
        level = member.getLevel();
        point = member.getPoint();
    }

    @Override
    public void onSuccess() {
        showToast("恭喜您，升级成功");
    }
}

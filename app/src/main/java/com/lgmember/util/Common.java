package com.lgmember.util;

/**
 * Created by Yanan_Wu on 2017/1/5.
 */

public class Common {
 public static boolean FLAG = false;
 /*//老服务器基地址
  public static  String URL_BASE = "http://221.212.177.245:8080/front/";*/
 //新服务器基地址
 public static  String URL_BASE = "http://221.212.183.238/";
 /***************************************游客相关的接口**************************************************/
 //游客登录
 public static String URL_GUEST_LOGIN = URL_BASE+ "guest-login";
 //判断当前是否是游客登录；根据登录的方式不同，显示不同的界面
 public static String URL_IF_GUEST_LOGIN = URL_BASE+ "get-member-name";
 //返回会员姓名
 public static String URL_GET_MEMBER_NAME = URL_BASE+"get-member-name";

 /***************************************登录接口**************************************************/
 //登录：调用此接口两次，一次get方式请求，一次post方式请求
 public static String URL_LOGIN = URL_BASE+ "login";
 //打开app赠送积分
 public static String URL_START_SCORES = URL_BASE+"member/app_addpoint";
 //忘记密码
 public static String URL_FORGET_PASSWORD = URL_BASE+"forgotpwd";
 //图形验证码
 public static String URL_CPT = URL_BASE+"cpt";

 /***************************************注册接口**************************************************/
 //注册
 public static String URL_REGISTER = URL_BASE+"mobile-reg";
 //获取手机验证码
 public static String URL_REQUEST_CODE = URL_BASE+"sms-capt";
 //判断手机验证码是否正确
 public static String URL_VALIDATE_SMS_CAPT = URL_BASE+"validate-sms-capt";//没有用上

 /***************************************个人资料接口**************************************************/
 //头像
 public static String URL_UPDATE_PHOTO = URL_BASE+"avatar-update";
 //详细资料
 public static String URL_MEMBER_MESSAGE = URL_BASE+"profile";
 //编辑个人资料
 public static String URL_EDIT_MEMBER_MESSAGE = URL_BASE+"profile-update";
 //实名认证上传照片
 public static String URL_UPLOAD_IMG = URL_BASE+"upload";
 //申请实名认证
 public static String URL_CERTIFICATION = URL_BASE+"auth";
 //sp保存实名认证结果
 public static String SP_AUTHORIZED = "sp_authorized";

 /***************************************活动管理接口**************************************************/
 //活动标签
 public static String URL_TAGS_LIST = URL_BASE+"project2/get-tag-list";
 //活动列表
 public static String URL_PROJECT_MESSAGE_ALLLIST = URL_BASE+"project2/all";
 //活动报名
 public static String URL_ACTIVITY_JOIN = URL_BASE+"project/check-in2";
 //以往报名
 public static String URL_ALREAD_JOIN = URL_BASE+"project2/history";
 //即将参与
 public static String URL_SOON_JOIN = URL_BASE+"project2/future";
 //热门活动
 public static String URL_HOT = URL_BASE+"project2/popular";

 /*********************************************俱乐部相关接口***************************************************/
 //俱乐部列表
 public static String URL_CLUB_LIST = URL_BASE+"club";
 //加入俱乐部
 public static String URL_ADD_CLUB = URL_BASE+"club/member-add";
 //俱乐部活动列表
 public static String URL_CLUB_ACTIVITY_LIST = URL_BASE+"club-project/all";
 //俱乐部下的活动报名
 public static String URL_CLUB_ACTIVITY_JOIN = URL_BASE+"club-project/check-in2";
 //图片基地址
 public static  String URL_IMG_BASE = URL_BASE+"project_img/";
 //首页—我的俱乐部
 public static String URL_MY_CLUB_LIST = URL_BASE+"club/my";

 /*********************************************我的积分接口***************************************************/
 //历史积分
 public static String URL_HISTORY_SCORES = URL_BASE+"point/history";
 //积分规则
 public static String URL_SCORES_RULE = URL_BASE+"point/rule";
 //积分升级
 public static String URL_SCORES_UPGRADE = URL_BASE+"member/upgrade";
 //积分消息
 public static String URL_SCORES_INFORMATION = URL_BASE+"point/information";//收件详情
 /*********************************************积分兑换接口***************************************************/
 //所有礼品
 public static String URL_EXCHANGE_ALL_GIGT = URL_BASE+"point/giftlist";
 //礼物详情
 public static String URL_EXCHANGE_GIFT_INFO = URL_BASE+"gift/information";
 //可兑换礼品
 public static String URL_EXCHANGE_SELECT_GIGT = URL_BASE+"point/giftexchangelist";
 //收件详情
 public static String URL_EXCHANGE_GIFT = URL_BASE+"point/exchange";
 //已兑换礼品
 public static String URL_EXCHANGE_ALREADY_GIGT = URL_BASE+"point/gift-send-list";

 /*********************************************我的卡券接口***************************************************/
 //各个状态的卡券列表
 public static String URL_CARD_LIST = URL_BASE+"coupon";
 //一键领取
 public static String URL_GET_CARD = URL_BASE+"coupon/get";
 //C: 获取卡券兑换码
 public static String URL_CARD_CODE = URL_BASE+"coupon/code";
 //领取提醒里的卡券
 public static String URL_GET_REMIND_CARD = URL_BASE+"get";
 /*********************************************我要签到接口***************************************************/
 //二维码活动签到
 public static String URL_PROJECT_SIGN = URL_BASE+"project2/sign-in";
 //二维码俱乐部签到
 public static String URL_CLUB_PROJECT_SIGN = URL_BASE+"club-project/sign-in";
 //录音签到
 public static String URL_UPLOAD_RECORD = URL_BASE+"radio-record/upload";
 /*********************************************我的消息接口***************************************************/
 //系统消息
 public static String URL_REMIND_LIST = URL_BASE+"remind/list";
 //获取各个阅读状态数量
 public static String URL_NOREAD_MESSAGE_NUM = URL_BASE+"message/get-count";
 //系统消息（提醒）更新为已读消息
 public static String URL_IF_READ = URL_BASE+"remind/read";
 //消息列表
 public static String URL_MESSAGE_LIST = URL_BASE+"message/list";
 //删除消息
 public static String URL_DELETE_MESSAGE = URL_BASE+"message/delete";//未使用
 //会员消息更新为已读消息
 public static String URL_UPDATE_MESSAGE_STATE = URL_BASE+"message/update";

 /**********************************************收藏接口*****************************************************/
 //活动收藏列表
 public static String URL_COLLECTION_LIST = URL_BASE+"project2/saved";

 //活动收藏列表
 public static String URL_CLUB_ACTIVITY_COLLECTION_LIST = URL_BASE+"club-project/saved";
 //活动添加收藏
 public static String URL_ADD_COLLECTION = URL_BASE+"favorite/add";
 //活动删除收藏
 public static String URL_DELETE_COLLECTION = URL_BASE+"favorite/delete";
 //俱乐部活动添加收藏
 public static String URL_CLUB_ADD_COLLECTION = URL_BASE+"club-favorite/add";
 //俱乐部活动删除收藏
 public static String URL_CLUB_DELETE_COLLECTION = URL_BASE+"club-favorite/delete";
 /**********************************************问题反馈接口*****************************************************/
 //创建问题反馈
 public static String URL_CREATE_FEEDBACK = URL_BASE+"create-feedback";
 //问题反馈列表
 public static String URL_FEEDBACK_LIST = URL_BASE+"feedback";
 //删除问题反馈
 public static String URL_DELETE_FEEDBACK = URL_BASE+"delete-feedback";

 /*********************************************全局参数******************************************************/
 //sp的名称
 public static String SP_NAME = "sp_name";
 //版本更新时的时间计算
 public static String SP_VERSION_UPDATE_TIME = "sp_version_update_time";
 //sp保存是否接受收推送消息
 public static String SP_IF_TUISONG = "sp_if_tuisong";
 //sp保存是否自动录音
 public static String SP_IF_RECORDER = "sp_if_recorder";
 /**********************************************设置接口*****************************************************/
 //修改密码
 public static String URL_MODIFY_PWD = URL_BASE+"pwd";
 //版本更新
 public static String URL_VERSION = URL_BASE+ "android-app-version";
 //下载apk
 public static String URL_APK = URL_BASE+ "android.apk";
 /**********************************************未使用将来可能有用接口*****************************************************/
 //会员招募信息详情
 public static String URL_PROJECT_MESSAGE_DETAIL = URL_BASE+"app-project-message";
 //录音节目识别结果查询
 public static String URL_RECORD_RESULT = URL_BASE+"radio-record/query";
 //录音识别列表
 public static String URL_RECORD_LIST = URL_BASE+"radio-record";

}

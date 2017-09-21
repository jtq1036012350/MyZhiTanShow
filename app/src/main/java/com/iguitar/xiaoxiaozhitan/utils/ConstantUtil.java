package com.iguitar.xiaoxiaozhitan.utils;

import android.Manifest;

/**
 * Created by Jiang on 2016/9/2.
 */
public interface ConstantUtil {
    public static final int FORRESULT = 100;
    //------------------------------------------------------------地址------------------------------------------------------//
    //店铺地址
    public static final String storeUrl = "https://xxzhitan.taobao.com/";
    //快手地址
    public static final String kuaishouUrl = "http://qr.kuaishou.com/u/GkXZEburWcM";
    //熊猫直播地址
    public static final String pandaUrl = "http://www.panda.tv/264305";

    //优酷地址
    public static final String youkuUrl = "http://i.youku.com/xxzhitan";

    //腾讯传课地址
    public static final String tencentUrl = "http://xxzt.ke.qq.com";

    //百度传课地址
    public static final String baiduchuankeUrl = "http://www.chuanke.com/s4522102.html";

    //南通指弹地址
    public static final String nantongUrl = "http://tieba.baidu.com/f?ie=utf-8&kw=指弹南通&fr=search&red_tag=x0723945520";

    //百度
    public static final String baiduUrl = "http://www.baidu.com";

    //包名：
    //熊猫
    public static final String packXiongMao = "com.panda.videoliveplatform";
    //快手
    public static final String packKuaiShou = "com.smile.gifmaker";

    //快速打开地址
    //熊猫：
    public static final String roomXiongMao = "pandatv://openroom/353190";
    //快手：
    public static final String roomKuaiShou = "kwai://profile/49466328";
    //--------------------------------------------------------------SharedPreferences-------------------------------------------//
    //头像
    public static final String HEADURL = "HEADURL";

    //--------------------------------------------------------------权限-------------------------------------------------------//
    /**
     * 位置信息权限请求标志
     */
    public static final int QUEST_CODE_LOCTION = 1;
    /**
     * 发送短信权限请求标志
     */
    public static final int QUEST_CODE_SEND_SMS = 2;
    /**
     * 摄像头权限标志
     */
    public static final int QUEST_CODE_CAMERA = 3;
    /**
     * 批量请求权限
     */
    public static final int QUEST_CODE_ALL = 4;
    /**
     * 拨打电话权限
     */
    public static final int QUEST_CODE_CALL_PHONE = 5;
    //要申请的权限
    public static final String[] permArray =
            {Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.SEND_SMS, Manifest.permission.CAMERA,
                    Manifest.permission.CALL_PHONE};
    //---------------------------------------------------------------------url集合---------------------------------------------------------//
    public static final String urlOne = "http://v.youku.com/v_show/id_XOTE2NTMwODE2.html";
    public static final String urlTwo = "http://v.youku.com/v_show/id_XOTI1MTU0ODQ4.html";
    public static final String urlThree = "http://v.youku.com/v_show/id_XOTM0OTM2Njcy.html";
    public static final String urlFour = "http://v.youku.com/v_show/id_XOTQ1NTkxNTg0.html";
    public static final String urlFive = "http://v.youku.com/v_show/id_XMTI2MjUwMTUwMA==.html";
    public static final String urlSix = "http://v.youku.com/v_show/id_XOTYwNjk5NjM2.html";
    public static final String urlSeven = "http://v.youku.com/v_show/id_XOTYwODQyODQ0.html";
    public static final String urlEight = "http://v.youku.com/v_show/id_XMTI0OTIzNzc3Mg==.html";
    public static final String urlNine = "http://v.youku.com/v_show/id_XMTI1Nzc3NjU0NA==.html";
    public static final String urlTen = "http://v.youku.com/v_show/id_XMTMyNjY2ODU0OA==.html";
    public static final String urlEleven = "http://v.youku.com/v_show/id_XMTMzMjM1NDQ2OA==.html";
    public static final String urlTweleve = "http://v.youku.com/v_show/id_XMTMzODA3MDk4NA==.html";
    public static final String urlThirteen = "http://v.youku.com/v_show/id_XMTM0OTYzMjc5Mg==.html";
    public static final String urlForteen = "http://v.youku.com/v_show/id_XMTM1NTkyMzIyMA==.html";
    public static final String urlFifteen = "http://v.youku.com/v_show/id_XMTM2MTUzMTczMg==.html";
    public static final String urlSixteen = "http://v.youku.com/v_show/id_XMTM2NzMxMDA4MA==.html";
    public static final String urlSeventeen = "http://v.youku.com/v_show/id_XMTM3NDMwMjAxMg==.html";
    public static final String urlEighteen = "http://v.youku.com/v_show/id_XMTM3OTI1MTIwNA==.html";
    public static final String urlNinteen = "http://v.youku.com/v_show/id_XMTM5NTkxNTEwNA==.html";
    public static final String urlTwenty = "http://v.youku.com/v_show/id_XMTQyOTYwNjY2NA==.html";
    public static final String urlTwentyOne = "http://v.youku.com/v_show/id_XMTQ0MDY0NDgzMg==.html";
    public static final String urlTwentyTwo = "http://v.youku.com/v_show/id_XMTQ0ODYzMTUwNA==.html";
    public static final String urlTwentyThree = "http://v.youku.com/v_show/id_XMTQ2NjAwMTA1Ng==.html";
    public static final String urlTwentyFour = "http://v.youku.com/v_show/id_XMTQ5ODI2NDg5Ng==.html";
    public static final String urlTwentyFive = "http://v.youku.com/v_show/id_XMTUzNzQyNTg3Ng==.html";
    public static final String urlTwentySix = "http://v.youku.com/v_show/id_XMTU3MDc2Njk2NA==.html";
    public static final String urlTwentySeven = "http://v.youku.com/v_show/id_XMTU5MDgwMDY0NA==.html";
    public static final String urlTwentyEight = "http://v.youku.com/v_show/id_XMTYxMDM3Njc1Ng==.html";
    public static final String urlTwentyNine = "http://v.youku.com/v_show/id_XMTcxOTMyMzU0NA==.html";
    public static final String urlThirty = "http://v.youku.com/v_show/id_XMTcyNzM2MzA4MA==.html";
    public static final String urlThirtyOne = "http://v.youku.com/v_show/id_XMTc0ODMwODI5Ng==.html";
    public static final String urlThirtyTwo = "http://v.youku.com/v_show/id_XMTc1ODc5ODk0MA==.html";
    public static final String urlThirtyThree = "http://v.youku.com/v_show/id_XMTc2OTA1OTA2OA==.html";
    public static final String urlThirtyFour = "http://v.youku.com/v_show/id_XMjUzNjA5NDQyMA==.html";
    public static final String urlThirtyFive = "http://v.youku.com/v_show/id_XMjYxMTQ4OTI1Mg==.html";
    public static final String urlThirtySix = "http://v.youku.com/v_show/id_XMTQwMTEwMjYwNA==.html";
    public static final String urlThirtySeven = "http://v.youku.com/v_show/id_XMTQwNjkzNzg3Mg==.html";
    public static final String urlThirtyEight = "http://v.youku.com/v_show/id_XMTQxMTU3MzQ3Mg==.html";
    public static final String urlThirtyNine = "http://v.youku.com/v_show/id_XMTQxNTE4NzIwMA==.html";
    public static final String urlForty = "http://v.youku.com/v_show/id_XMTQxNTI3NDU4MA==.html";
    public static final String urlFortyOne = "http://v.youku.com/v_show/id_XMTUwNDE4MzAzMg==.html";
    public static final String urlFortyTwo = "http://v.youku.com/v_show/id_XMTUyMDIyMDg4MA==.html";
    public static final String urlFortyThree = "http://v.youku.com/v_show/id_XMTc5NDA4NTUyMA==.html";
    public static final String urlFortyFour = "http://v.youku.com/v_show/id_XMTgxNDI3MDg2MA==.html";
    public static final String urlFortyFive = "http://v.youku.com/v_show/id_XMTgyMjkyOTU2NA==.html";
    public static final String urlFortySix = "http://v.youku.com/v_show/id_XMTg0NDA0OTU1Ng==.html";
    public static final String urlFortySeven = "http://v.youku.com/v_show/id_XMTg1ODgxNDQ5Ng==.html";
    public static final String urlFortyEight = "http://v.youku.com/v_show/id_XMTI2NDU3MzA0NA==.html";

    //apache更新地址
    public static final String internretUrl = "http://192.168.10.19:8080/XiaoXiao/";
}

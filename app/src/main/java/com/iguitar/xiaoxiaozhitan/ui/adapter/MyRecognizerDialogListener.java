package com.iguitar.xiaoxiaozhitan.ui.adapter;

import android.app.Activity;
import android.widget.ListView;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iguitar.xiaoxiaozhitan.model.Conversation;
import com.iguitar.xiaoxiaozhitan.model.MyConversionBean;
import com.iguitar.xiaoxiaozhitan.model.VoiceBean;
import com.iguitar.xiaoxiaozhitan.model.VoiceUtils;
import com.iguitar.xiaoxiaozhitan.utils.CommonUtil;

import java.util.List;

/**
 * 语音监听类
 * Created by Jiang on 2017-09-20.
 */

public class MyRecognizerDialogListener implements RecognizerDialogListener {
    private StringBuilder sb;
    private List<Conversation> listData;
    private ListView listView;
    private VoiceUtils voiceUtils;
    private MyVoiceAdapter adapter;
    private List<MyConversionBean> myConversionBeanList;
    private Activity context;

    @Override
    public void onError(SpeechError arg0) {

    }

    public MyRecognizerDialogListener(Activity context, List<Conversation> listData, ListView listView, MyVoiceAdapter adapter, VoiceUtils voiceUtils, List<MyConversionBean> myConversionBeanList) {
        this.context = context;
        this.voiceUtils = voiceUtils;
        this.adapter = adapter;
        this.listView = listView;
        this.listData = listData;
        this.myConversionBeanList = myConversionBeanList;
        sb = new StringBuilder();
    }

    public String processJson(String result) {
//		System.out.println(result);
        Gson gson = new Gson();
        VoiceBean voiceBean = gson.fromJson(result, VoiceBean.class);
        List<VoiceBean.WS> ws = voiceBean.ws;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ws.size(); i++) {
            sb.append(ws.get(i).cw.get(0).w);
        }
        String string = sb.toString();
//		System.out.println(string);
        return string;
    }

    @Override
    public void onResult(RecognizerResult result, boolean isLast) {
        String processJson = processJson(result.getResultString());
        sb.append(processJson);
        if (isLast) {//是最后一句
            String askerText = sb.toString();
            // 清空sb，把上一段话清空
            sb = new StringBuilder();

            // 创建提问会话对象
            Conversation asker = new Conversation(askerText, true, -1);
            listData.add(asker);


            // 根据提问内容回答

            String answerText = "";
            int imgId = -1;
//            if(askerText.contains("你好")){
//                answerText = "你也好";
//            }else if(askerText.contains("美女")){
//                Random random = new Random();
//                int nextInt = random.nextInt(Resources.imgids.length);
//                answerText = Resources.words[nextInt];
//                imgId = Resources.imgids[nextInt];
//            }
            boolean findFlag = false;
            if (myConversionBeanList == null || myConversionBeanList.size() == 0) {
                findFlag = false;
                CommonUtil.showTopToast(context, "您的服务可能有点问题，请检查！");
            } else {
                for (MyConversionBean myConversionBean : myConversionBeanList) {
                    if (askerText.contains(myConversionBean.getAskString())) {
                        answerText = myConversionBean.getResponseString();
                        findFlag = true;
                        break;
                    }
                }
            }
            if (!findFlag) {
                answerText = "你说啥，听不懂";
            }

            Conversation answer = new Conversation(answerText, false, imgId);
            listData.add(answer);
            adapter.notifyDataSetChanged();
            // 自动滑倒最后
            listView.setSelection(listData.size());
            // 读出回答内容
            voiceUtils.speak(answerText);
        }
    }

}
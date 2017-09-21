package com.iguitar.xiaoxiaozhitan.model;

import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

public class VoiceUtils {
	private Context context;

	public VoiceUtils(Context context){
		this.context = context;
		// 将“12345678”替换成您申请的APPID，申请地址：http://open.voicecloud.cn  
		SpeechUtility.createUtility(context, SpeechConstant.APPID +"=59c1b4ac");
	}
	
	// 声音转文字
	public void listen(RecognizerDialogListener listener){
		//1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener  
		RecognizerDialog iatDialog = new RecognizerDialog(context,null);
		//2.设置听写参数，同上节  
		iatDialog.setParameter(SpeechConstant.DOMAIN, "iat");
		iatDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		iatDialog.setParameter(SpeechConstant.ACCENT, "mandarin ");
		//3.设置回调接口  
		iatDialog.setListener(listener);  
		//4.开始听写  
		iatDialog.show(); 
	}
	// 文字转声音
	public void speak(String word){
		//1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener  
		SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer(context, null);
		//2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类  
		mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
		mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
		mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
		mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
		//设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”  
		//保存在SD卡需要在AndroidManifest.xml添加写SD卡权限  
		//如果不需要保存合成音频，注释该行代码  
		mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
		//3.开始合成  
		mTts.startSpeaking(word, null);    
	}
}

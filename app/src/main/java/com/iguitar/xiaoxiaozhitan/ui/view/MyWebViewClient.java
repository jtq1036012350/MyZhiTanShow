package com.iguitar.xiaoxiaozhitan.ui.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.andview.refreshview.XRefreshView;
import com.iguitar.xiaoxiaozhitan.utils.CommonUtil;

/**
 * MyWebViewClient
 * Created by Jiang on 2017/4/17.
 */

public class MyWebViewClient extends WebViewClient {
    private Activity mActivity;
    private XRefreshView mXRefreshView;

    private OnSuccessLoadListener onSuccessLoadListener;

    public interface OnSuccessLoadListener {
        void onSuccessLoad(int flag);
    }

    public void setonSuccessLoadListener(OnSuccessLoadListener onSuccessLoadListener) {
        this.onSuccessLoadListener = onSuccessLoadListener;
    }

    public MyWebViewClient(Activity mActivity, XRefreshView mXRefreshView) {
        this.mActivity = mActivity;
        this.mXRefreshView = mXRefreshView;
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        //网页加载失败的处理，一般是出错图片，跳转到出错处理页面
        super.onReceivedError(view, errorCode, description, failingUrl);
        if (CommonUtil.getNetype(mActivity) == -1) {
            CommonUtil.showTopToast(mActivity, "网络错误！请稍后重试！");
        } else {
            if (onSuccessLoadListener != null) {
                onSuccessLoadListener.onSuccessLoad(-1);
            }
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        //网页加载结束的处理，可以停止动画
//        PrompUtil.stopProgressDialog("加载中...");
        mXRefreshView.stopRefresh();

    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {

        super.onPageFinished(view, url);

        //网页加载开始的处理，开始动画
//        PrompUtil.startProgressDialog(mActivity,"加载中...");
        mXRefreshView.startRefresh();
    }

    /**
     * Description: handle https
     * Created by Jiang on 12/6/16 08:38
     */
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }

    /**
     * 解决淘宝打不开的问题
     *
     * @param view
     * @param url
     * @return
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//        view.loadUrl(url);
//        try{
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            mActivity.startActivity( intent );
//        }catch(Exception e){
//            LogUtil.d("errorInfo",e.toString());
//        }
//        if (url.contains("taobao")) {
//
//        } else {
//            view.loadUrl(url);
//        }
        if (url.startsWith("http:") || url.startsWith("https:")) {
            return super.shouldOverrideUrlLoading(view, url);
        } else if(url.startsWith("intent://play")){
                return true;
        }else{
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                mActivity.startActivity(intent);
            } catch (Exception ex) {
                boolean a = url.contains("panda");
                boolean sa = url.contains("kwai");
                if (url.contains("pandatv://")) {
                    url = "pandatv://openroom/353190";
                } else if (url.contains("kwai://")) {
                    url = "kwai://profile/49466328";
                }
            }
            //  下面这一行保留的时候，原网页仍报错，新网页正常.所以注释掉后，也就没问题了
            //          view.loadUrl(url);
//            return false;
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

}

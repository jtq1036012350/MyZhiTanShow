package com.iguitar.xiaoxiaozhitan.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iguitar.xiaoxiaozhitan.R;
import com.iguitar.xiaoxiaozhitan.model.Conversation;

import java.util.List;

/**
 * 语音Adapter
 * Created by Jiang on 2017-09-20.
 */

public class MyVoiceAdapter extends BaseAdapter {
    private List<Conversation> listData;
    private Context context;

    public MyVoiceAdapter(Context context,List<Conversation> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if(convertView==null){
            convertView = View.inflate(context, R.layout.fragment_voice_item, null);
            holder = new Holder();
            holder.asker = (TextView) convertView.findViewById(R.id.tv_asker);
            holder.answer_ll = (LinearLayout) convertView.findViewById(R.id.ll_answer);
            holder.answer_iv = (ImageView) convertView.findViewById(R.id.iv_answer);
            holder.answer_tv = (TextView) convertView.findViewById(R.id.tv_answer);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        Conversation conversation = listData.get(position);
        if(conversation.isAsker){// 是提问
            holder.asker.setVisibility(View.VISIBLE);
            holder.answer_ll.setVisibility(View.GONE);
            holder.asker.setText(conversation.word);
        }else{// 是回答
            holder.asker.setVisibility(View.GONE);
            holder.answer_ll.setVisibility(View.VISIBLE);
            if(conversation.imgId==-1){// 是否有图片
                holder.answer_iv.setVisibility(View.GONE);
            }else{
                holder.answer_iv.setVisibility(View.VISIBLE);
                holder.answer_iv.setImageResource(conversation.imgId);
            }
            holder.answer_tv.setText(conversation.word);
        }
        return convertView;
    }

    class Holder{
        public TextView asker;
        public LinearLayout answer_ll;
        public TextView answer_tv;
        public ImageView answer_iv;
    }

}

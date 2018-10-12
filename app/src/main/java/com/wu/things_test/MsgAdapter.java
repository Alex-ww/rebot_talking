package com.wu.things_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/11.
 */
public class MsgAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private List<Msg> msgList=new ArrayList<>();

    public MsgAdapter(List<Msg> msgList, Context context){
        this.msgList=msgList;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return msgList.size();
    }

    @Override
    public Msg getItem(int position) {
        return msgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Msg msg=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view=inflater.inflate(R.layout.chat_item,null);
            viewHolder=new ViewHolder();
            viewHolder.leftLayout= (LinearLayout) view.findViewById(R.id.left_layout);
            viewHolder.leftMsg= (TextView) view.findViewById(R.id.left_msg);
            viewHolder.rightLayout= (LinearLayout) view.findViewById(R.id.right_layout);
            viewHolder.rightMsg= (TextView) view.findViewById(R.id.right_msg);
            viewHolder.date= (TextView) view.findViewById(R.id.Date);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }
        if(msg.getType()==Msg.TYPE_RECIVER){
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.leftMsg.setText(msg.getContent().toString());
            viewHolder.date.setVisibility(View.GONE);
        }else if(msg.getType()==Msg.TYPE_SEND){
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.rightMsg.setText(msg.getContent().toString());
            viewHolder.date.setVisibility(View.VISIBLE);
            viewHolder.date.setText(msg.getDateStr());
        }
        return view;
    }
    class ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
        TextView date;
    }

}

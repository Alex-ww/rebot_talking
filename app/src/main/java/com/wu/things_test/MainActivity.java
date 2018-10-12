package com.wu.things_test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private ListView msgList;
    private EditText message;
    private Button send;
    MsgAdapter adapter;
    List<Msg> messList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMsg();
        msgList = (ListView) findViewById(R.id.mes_list);
        message = (EditText) findViewById(R.id.msg_text);
        send = (Button) findViewById(R.id.send);
        adapter = new MsgAdapter(messList, this);
        msgList.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String content = message.getText().toString();
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SEND);
                    messList.add(msg);
                    adapter.notifyDataSetChanged();
                    msgList.setSelection(messList.size());
                    message.setText("");

                    new Thread() {
                        public void run() {
                            Msg from = null;
                            try {
                                from = HttpUtils.sendMessage(content);
                            } catch (Exception e) {
                                from = new Msg("服务器挂了呢...", Msg.TYPE_RECIVER);
                            }
                            Message message = Message.obtain();
                            message.obj = from;
                            mHandler.sendMessage(message);
                        }
                    }.start();
                }
            }
        });
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Msg from = (Msg) msg.obj;
            messList.add(from);
            adapter.notifyDataSetChanged();
            msgList.setSelection(messList.size()-1);
        }
    };

    private void initMsg() {
        Msg msg1 = new Msg("你好？我们交个朋友吧！", Msg.TYPE_RECIVER);
        messList.add(msg1);
        /**Msg msg2 = new Msg("我是隔壁小王，你是谁？", Msg.TYPE_SEND);
        messList.add(msg2);
        Msg msg3 = new Msg("what？你真不知道我是谁吗？哭~", Msg.TYPE_RECIVER);
        messList.add(msg3);
        Msg msg4 = new Msg("大哥，别哭，我真不知道", Msg.TYPE_SEND);
        messList.add(msg4);
        Msg msg5 = new Msg("卧槽，你不知道你来撩妹？", Msg.TYPE_RECIVER);
        messList.add(msg5);*/
    }
}

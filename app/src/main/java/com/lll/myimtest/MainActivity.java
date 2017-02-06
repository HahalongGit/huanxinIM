package com.lll.myimtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lll.myimtest.activitys.ChatActivity;
import com.lll.myimtest.utils.LogUtils;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private EditText edit_userID;

    private Button btn_submit;
;
    private Button btn_loginOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_userID = (EditText) findViewById(R.id.edit_userID);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_loginOut = (Button) findViewById(R.id.btn_loginOut);
        btn_submit.setOnClickListener(this);
        btn_loginOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:{
                LogUtils.e(TAG,"发送消息");
                String input = edit_userID.getText().toString();
                startChat(input);
                break;
            }
            case R.id.btn_loginOut:{
                LogUtils.e(TAG,"退出登录");
                loginOut();
                break;
            }
        }
    }

    /**
     * 发起聊天
     * @param input
     */
    private void startChat(String input) {
        //new出EaseChatFragment或其子类的实例
        if(!TextUtils.isEmpty(input)){
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            intent.putExtra("userID",input);
            startActivity(intent);
        }else{
            Toast.makeText(this, "请输入一个用户id", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 退出登录
     */
    private void loginOut() {
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "您已退出登录！", Toast.LENGTH_SHORT).show();
                    }
                });
                LogUtils.e(TAG,"退出-onSuccess");
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub
                LogUtils.e(TAG,"退出-onProgress"+status);
            }

            @Override
            public void onError(int code, final String message) {
                LogUtils.e(TAG,"退出-onProgress"+message);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "退出失败，"+message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}

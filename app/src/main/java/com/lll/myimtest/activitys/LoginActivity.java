package com.lll.myimtest.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.lll.myimtest.MainActivity;
import com.lll.myimtest.R;
import com.lll.myimtest.utils.LogUtils;
import com.lll.myimtest.utils.SharePreferenceTool;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edit_userName,edit_userPass;

    private Button btn_login;

    private static final String TAG = "LoginActivity";

    private TextView tv_goRegist;

    private Button btn_loginOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_userName = (EditText) findViewById(R.id.edit_userName);
        edit_userPass = (EditText) findViewById(R.id.edit_userPass);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_goRegist = (TextView) findViewById(R.id.tv_goRegist);
        btn_loginOut = (Button) findViewById(R.id.btn_loginOut);
        String userName = SharePreferenceTool.getString("userName");
        String pasword = SharePreferenceTool.getString("pasword");
        if(userName!=null && pasword!=null){
            edit_userName.setText(userName);
            edit_userPass.setText(pasword);
        }
        btn_login.setOnClickListener(this);
        tv_goRegist.setOnClickListener(this);
        btn_loginOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:{
                LogUtils.e(TAG,"点击登录");
                String username = edit_userName.getText().toString().trim();
                String password = edit_userPass.getText().toString().trim();
                LogUtils.e(TAG,"username："+username+",password:"+password);
                login(username,password);
                break;
            }case R.id.tv_goRegist:{
                //registUser(edit_userName.getText().toString().trim(),edit_userPass.getText().toString().trim());
                Intent intent = new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(intent);
                finish();
                break;
            }case R.id.btn_loginOut:{
                loginOut();
                break;
            }
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
                        Toast.makeText(LoginActivity.this, "您已退出登录！", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(LoginActivity.this, "退出失败，"+message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 用户注册
     * @param userName
     * @param pasword
     */
    private void registUser(final String userName, final String pasword) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtils.e(TAG,"注册用户："+userName);
                try {
                    EMClient.getInstance().createAccount(userName, pasword);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.e(TAG,"注册状态"+e.getMessage());
                }
            }
        }).start();
    }

    /**
     * 用户登录
     * @param username
     * @param password
     */
    private void login(String username,String password) {
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "用户名或者密码为空！", Toast.LENGTH_SHORT).show();
        }else{
            EMClient.getInstance().login(username,password,new EMCallBack() {//回调
                @Override
                public void onSuccess() {
                    LogUtils.e(TAG, "登录聊天服务器成功！");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onProgress(int progress, String status) {
                    LogUtils.e(TAG,"登录的进度："+progress+",status:"+status);
                }

                @Override
                public void onError(int code, final String message) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "登录失败，"+message, Toast.LENGTH_SHORT).show();
                        }
                    });
                    LogUtils.e("main", "onError-登录聊天服务器失败！"+message);
                }
            });
        }
    }
}

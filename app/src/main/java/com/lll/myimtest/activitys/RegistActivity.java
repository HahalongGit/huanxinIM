package com.lll.myimtest.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.lll.myimtest.R;
import com.lll.myimtest.utils.SharePreferenceTool;

public class RegistActivity extends AppCompatActivity implements OnClickListener{

    private static final String TAG = "RegistActivity";

    private Button btn_regist;
    private EditText edit_regist,edit_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        btn_regist = (Button) findViewById(R.id.btn_regist);
        edit_pass = (EditText) findViewById(R.id.edit_pass);
        edit_regist = (EditText) findViewById(R.id.edit_regist);
        btn_regist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_regist:{
                Log.e(TAG,"点击注册");
                String userName = edit_regist.getText().toString();
                String pasword = edit_pass.getText().toString();
                registUser(userName,pasword);
                break;
            }
        }
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
                Log.e(TAG,"注册用户："+userName);
                try {
                    EMClient.getInstance().createAccount(userName, pasword);
                    SharePreferenceTool.saveString(userName,userName);
                    SharePreferenceTool.saveString(pasword,pasword);
                    startActivity(new Intent(RegistActivity.this,LoginActivity.class));
                    finish();
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    Log.e(TAG,"注册状态"+e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegistActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}

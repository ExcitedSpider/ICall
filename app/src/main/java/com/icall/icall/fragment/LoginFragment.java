package com.icall.icall.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.icall.icall.LoginActivity;
import com.icall.icall.R;
import com.icall.icall.bean.User;
import com.icall.icall.util.HttpUtil;


//Todo:
//当服务器上线后，编写登陆确认逻辑函数：
//private Boolean canLogin(User user)

public class LoginFragment extends Fragment implements View.OnClickListener{

    private View view;
    private Activity activity;
    ProgressDialog progressDialog = null;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_login, container, false);

        Button sendButton = view.findViewById(R.id.send);
        sendButton.setOnClickListener(this);

        Button registerButton = view.findViewById(R.id.register);
        registerButton.setOnClickListener(this);

        activity = getActivity();

        return view;
    }

    public void login() {
        EditText account = view.findViewById(R.id.account);
        EditText password = view.findViewById(R.id.password);

        User user = new User();
        user.setAccount(account.getText().toString());
        user.setPassword(password.getText().toString());

        final Boolean result = canLogin(user);
        try {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    closeProgressingDialog(result);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        Boolean result;
        switch (view.getId()){
            case R.id.send:
                makeProgressDialog();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        login();
                    }
                }).start();
                break;
            case R.id.register:
                LoginActivity la = (LoginActivity)activity;
                la.switchToFragment(new RegisterFragment());
        }
    }

    public void closeProgressingDialog(Boolean result) {
        //关闭dialog的回调方法
        progressDialog.cancel();
        if(result){
            Toast.makeText(activity,"登陆成功！",Toast.LENGTH_SHORT).show();
            activity.finish();
        }else {
            Toast.makeText(activity,"账号信息有误或网络不通畅",Toast.LENGTH_SHORT).show();
        }
    }


    private void makeProgressDialog(){
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("正在登陆中...");
        progressDialog.setMessage("请等待");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private Boolean canLogin(User user)
    {//本方法是检查是否能够登陆

        return HttpUtil.tryLogin(user.getAccount(),user.getPassword());
    }
}

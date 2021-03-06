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

import com.icall.icall.R;
import com.icall.icall.bean.User;
import com.icall.icall.util.HttpUtil;
import com.icall.icall.util.StringLocalCheck;


public class RegisterFragment extends Fragment implements View.OnClickListener{

    private View view;
    Activity activity;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_register, container, false);

        activity = getActivity();

        Button cancelButton = view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(this);
        Button commitButton = view.findViewById(R.id.commit_button);
        commitButton.setOnClickListener(this);

        return view;
    }
    private boolean localCheck(User user){
        //todo:加入更多本地检验逻辑
        //本地检验输入逻辑
        EditText rePassword = view.findViewById(R.id.re_password);
        EditText rePasswordC = view.findViewById(R.id.re_password_confirm);
        EditText reAccount = view.findViewById(R.id.re_account);
        if(!rePassword.getText().toString().equals(rePasswordC.getText().toString())){
            Toast.makeText(activity,"两次输入密码不相同",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String message = StringLocalCheck.checkRegisterUserProfile(user);
            if(message.equals("true"))
                return true;
            else {
                Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }

    private User getUserProfile(){
        EditText reAccount = view.findViewById(R.id.re_account);
        EditText rePassword = view.findViewById(R.id.re_password);
        EditText reSchool = view.findViewById(R.id.re_school);
        EditText reJob = view.findViewById(R.id.re_job);

        return new User(reAccount.getText().toString(),
                rePassword.getText().toString(),
                reSchool.getText().toString(),
                reJob.getText().toString());

    }

    private boolean pushUserProfile(User user){
        //本函数是将本地注册信息上传
        //若创建不成功，则返回false
        return HttpUtil.tryRegister(user);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel_button:
                getFragmentManager().popBackStack();break;
            case R.id.commit_button:
                final User user = getUserProfile();
                if(localCheck(user))
                {
                    makeProgressDialog();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(pushUserProfile(user)){
                                onCreateProfileSuccess();
                            }else {
                                onCreateProfileFailed();
                            }
                        }
                    }).start();break;

                }
        }
    }
    private void onCreateProfileSuccess(){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity,"创建成功！您可以登陆了！",Toast.LENGTH_SHORT).show();
                closeProgressingDialog();
                getFragmentManager().popBackStack();
            }
        });
    }
    private void onCreateProfileFailed(){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity,"创建失败！服务器炸了！",Toast.LENGTH_SHORT).show();
                closeProgressingDialog();
            }
        });
    }

    public void closeProgressingDialog() {
        progressDialog.cancel();
    }

    private void makeProgressDialog(){
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("正在与服务器通信中...");
        progressDialog.setMessage("请等待");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

}

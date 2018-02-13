package com.icall.icall;

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
    private boolean localCheck(){
        //本地检验输入逻辑
        EditText rePassword = view.findViewById(R.id.re_password);
        EditText rePasswordC = view.findViewById(R.id.re_password_confirm);
        EditText reAccount = view.findViewById(R.id.re_account);
        if(!rePassword.getText().toString().equals(rePasswordC.getText().toString())){
            Toast.makeText(activity,"两次输入密码不相同",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(rePassword.getText().toString().length()<=8){
            Toast.makeText(activity,"密码长度应大于8",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(reAccount.getText().toString().length()<=8){
            Toast.makeText(activity,"账号长度应大于8",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
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
        //本函数是将本地注册信息上传，服务器部署时重写
        //若创建不成功，则返回false
        try {
            Thread.sleep(3000);
            //模拟耗时
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel_button:
                getFragmentManager().popBackStack();break;
            case R.id.commit_button:
                if(localCheck())
                {
                    makeProgressDialog();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(pushUserProfile(getUserProfile())){
                                onCreateProfileSuccess();
                            }else {
                                onCreateProfileFailed();
                            }
                        }
                    }).start();

                }
        }
    }
    private void onCreateProfileSuccess(){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity,"创建成功！您可以登陆了！",Toast.LENGTH_SHORT).show();
                closeProgressingDialog();
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

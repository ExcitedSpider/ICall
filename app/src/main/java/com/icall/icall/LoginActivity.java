package com.icall.icall;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.icall.icall.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = null;
    private Fragment fragment = null;
    private String imei = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        askForPermisson(1);

        fragment = new LoginFragment();
        switchToFragment(fragment);

    }

    public void switchToFragment(Fragment fragment) {
        fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.lfragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public String getImei() {
        return imei;
    }

    public void askForPermisson(int requestCode){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("打扰一下")
                .setMessage("ICall需要一些权限以便让您持久登陆")
                .setCancelable(false)
                .setPositiveButton("了解", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(LoginActivity.this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
                    }
                }).show();

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1://读取手机状态
                if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    imei = "imei";
                }else {
                    TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                    try {
                        imei = tm.getDeviceId();
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}

package com.icall.icall;

import android.util.Log;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 冯秋翼 on 2018/2/15.
 */

public class HttpUtil {
    private final static String URL_ROOT = "http://192.168.1.15:8080";
    //change this when test on real phone

    static boolean tryLogin(String username,String password){
        OkHttpClient client = new OkHttpClient();

        Log.d("HttpUtil","LoginUser:"+username);
        RequestBody requestBody = new FormBody.Builder()
                .add("account",username)
                .add("password",password)
                .build();

        Request request = new Request.Builder()
                .url(URL_ROOT+"/login")
                .post(requestBody)
                .build();
        String message = "false";

        return postAndGetResult(client,request);
    }
    static boolean tryRegister(User user){
        OkHttpClient client = new OkHttpClient();

        Log.d("HttpUtil","RegisterUser:"+user.getAccount());

        RequestBody requestBody = new FormBody.Builder()
                .add("account",user.getAccount())
                .add("password",user.getPassword())
                .add("school",user.getSchool())
                .add("job",user.getJob())
                .build();

        Request request = new Request.Builder()
                .url(URL_ROOT+"/register")
                .post(requestBody)
                .build();

        return postAndGetResult(client,request);
    }

    static private boolean postAndGetResult(OkHttpClient client,Request request){
        String message = "false";

        try {
            Response response = client.newCall(request).execute();
            message = response.body().string();
            Log.d("httpUtil",message);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return message.equals("true");
    }
}

package com.icall.icall.util;

import android.util.Log;

import com.google.gson.Gson;
import com.icall.icall.bean.User;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 冯秋翼 on 2018/2/15.
 */

public class HttpUtil {
    private static Map form = null;
    private final static String URL_ROOT = "http://10.0.2.2:8080";
    //Change this when IP changed.
    //http://10.0.2.2:8080 is the local computer host

    public static boolean tryLogin(String username,String password,String imei){
        OkHttpClient client = new OkHttpClient();

        Log.d("HttpUtil","LoginUser:"+username);
        RequestBody requestBody = new FormBody.Builder()
                .add("account",username)
                .add("password",password)
                .add("imei",imei)
                .build();

        Request request = new Request.Builder()
                .url(URL_ROOT+"/login")
                .post(requestBody)
                .build();

        return postAndGetResult(client,request).get("login").equals("true");
    }
    public static boolean tryRegister(User user){
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

        return postAndGetResult(client,request).get("register").equals("true");
    }

    static private Map postAndGetResult(OkHttpClient client,Request request){
        String message = "false";

        try {
            Response response = client.newCall(request).execute();
            form = new Gson().fromJson(response.body().string(),Map.class);
            Log.d("HttpUtil","Login Response Message:"+form.toString());
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return form;
    }

}

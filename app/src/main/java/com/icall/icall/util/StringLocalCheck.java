package com.icall.icall.util;

import com.icall.icall.bean.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 冯秋翼 on 2018/2/15.
 */

public class StringLocalCheck {

    public static String checkRegisterUserProfile(User user){
        if(!accountMatch(user.getAccount()).equals("true")){
            return accountMatch(user.getAccount());
        }else if(!passwordMatch(user.getPassword()).equals("true")){
            return passwordMatch(user.getPassword());
        }else if(!schoolMatch(user.getSchool()).equals("true")){
            return schoolMatch(user.getSchool());
        }else if(!jobMatch(user.getJob()).equals("true")){
            return jobMatch(user.getJob());
        }else {
            return "true";
        }
    }

    public static String accountMatch(String account){
        Pattern pattern = null;
        Matcher matcher = null;

        //匹配用户名
        pattern = Pattern.compile("[a-zA-Z]{1}[a-zA-Z0-9_]{7,17}");
        matcher = pattern.matcher(account);
        if(!matcher.matches()){
            return "账号长度应介于8至18并且由字母数字下划线构成，且首位必须是字母";
        }
        return "true";
    }

    public static String passwordMatch(String password){
        Pattern pattern = null;
        Matcher matcher = null;

        //匹配用户名
        pattern = Pattern.compile("[a-zA-Z0-9]{8,18}");
        matcher = pattern.matcher(password);
        if(!matcher.matches()){
            return "密码长度应介于8至18并且由字母与数字构成";
        }
        return "true";
    }

    public static String schoolMatch(String school){
        Pattern pattern = null;
        Matcher matcher = null;

        //匹配用户名
        pattern = Pattern.compile("[a-zA-Z\\u4e00-\\u9fa5]{1,10}");
        matcher = pattern.matcher(school);
        if(!matcher.matches()){
            return "学校名称长度应介于1至10并且由中文字母数字构成";
        }
        return "true";
    }

    public static String jobMatch(String job){
        Pattern pattern = null;
        Matcher matcher = null;

        //匹配用户名
        pattern = Pattern.compile("[a-zA-Z\\u4e00-\\u9fa5]{1,10}");
        matcher = pattern.matcher(job);
        if(!matcher.matches()){
            return "职位名称长度应介于1至10并且由中文字母数字构成";
        }
        return "true";
    }
}

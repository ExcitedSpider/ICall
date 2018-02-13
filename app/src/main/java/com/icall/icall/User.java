package com.icall.icall;

/**
 * Created by 冯秋翼 on 2018/2/13.
 */

public class User {
    /*A bean of user profile*/

    private String account;
    private String password;
    private String school;
    private String job;

    public User(){};
    public User(String account,String password,String school,String job){
        this.account = account;
        this.password = password;
        this.school = school;
        this.job = job;
    }

    public String getAccount() {
        return account;
    }

    public String getJob() {
        return job;
    }

    public String getSchool(){
        return school;
    }

    public String getPassword() {
        return password;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}

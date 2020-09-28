package com.example.nahimana.imanage.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUserData {
    private static SharedUserData userInstance;
    private static Context mContext;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_BALANCE = "balance";
    private static final String KEY_TOKEN = "token";

    private SharedUserData(Context context){
        mContext = context;
    }
    public static synchronized SharedUserData getInstance(Context context) {
    if (userInstance == null) {
        userInstance = new SharedUserData(context);
    }
    return userInstance;
}
public boolean userLogin(String user_id, String username,String phone, String balance, String token){
    SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sp.edit();

    editor.putString(KEY_USER_ID, user_id);
    editor.putString(KEY_USERNAME, username);
    editor.putString(KEY_PHONE, phone);
    editor.putString(KEY_BALANCE, balance);
    editor.putString(KEY_TOKEN, token);
    editor.apply();
    return  true;
}
public boolean isLoggedIn() {
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sp.getString(KEY_USERNAME,null) !=null) {
            return true;
        }
        return  false;
}
public boolean logout(){
        SharedPreferences sp= mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
        return true;
}
public String getUsername()
{
    SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
    return sp.getString(KEY_USERNAME,null);
}
    public String getPhone()
    {
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sp.getString(KEY_PHONE,null);
    }
    public String getUserId()
    {
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sp.getString(KEY_USER_ID,null);
    }
    public String getBalance()
    {
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sp.getString(KEY_BALANCE,null);
    }
    public String getToken() {
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sp.getString(KEY_TOKEN,null);
    }


}

package com.example.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedPref {

    public static final String SHARED_PREF_NAME = "larntech";
    //Username
    public static final String USER_NAME = "username";
    public static SharedPref mInstance;
    public static Context mCtx;


    public SharedPref(Context context) {
        mCtx=context;
    }

    public  static  synchronized  SharedPref getInstance(Context context)
    {
        if(mInstance==null){
            mInstance=new SharedPref(context);
        }
        return  mInstance;
    }


    //store user data
    public  void storeUserName(String name)
    {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(USER_NAME,name);
        editor.clear();

    }
    //check if user is logged in
    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return  sharedPreferences.getString(USER_NAME,null)!=null;
    }

    public String LoggedInUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_NAME, null);

    }



    public void logout()
    {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
        mCtx.startActivity(new Intent(mCtx, MainActivity.class));


    }





}

package com.example.omar.myapplication;

import android.content.Context;
import android.content.SharedPreferences;


public class sharedprefmanger {
    private static sharedprefmanger instance;
    private static Context context1;
    private static final String SHARED_PREF_NAME="pref12";
    private static final String KEY_USERNAME="username";
    private static final String KEY_USER_EMAIL="phone";
    private static final String KEY_USER_ID="userid";
    private static final String KEY_GROUP_ID="groupid";
    private static final String KEY_USER_image="imagee";


    public sharedprefmanger(Context context) {
        this.context1=context;
 ;
    }
    public static synchronized sharedprefmanger getInstance(Context context)
    {
        if (instance==null){
            instance=new sharedprefmanger(context);
        }
        return instance;
    }
    public  boolean userIlogin(int id ,String email,String image,String username,int groupID)
    {
        SharedPreferences sharedPreferences=context1.getSharedPreferences(SHARED_PREF_NAME,context1.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putInt(KEY_USER_ID,id);
        editor.putString(KEY_USERNAME,username);
        editor.putString(KEY_USER_EMAIL,email);
        editor.putString(KEY_USER_image,image);
        editor.putInt(KEY_GROUP_ID,groupID);

        editor.apply();
        return true;

    }
    public  boolean islogin()
    {
        SharedPreferences sharedPreferences=context1.getSharedPreferences(SHARED_PREF_NAME,context1.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USERNAME,null)!=null)
        {
            return true;
        }
        return false;
    }
    public  boolean islogout()
    {
        SharedPreferences sharedPreferences=context1.getSharedPreferences(SHARED_PREF_NAME,context1.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;

    }
    public String getUsername(){
        SharedPreferences sharedPreferences=context1.getSharedPreferences(SHARED_PREF_NAME,context1.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME,null);
    }
    public String getEmail(){
        SharedPreferences sharedPreferences=context1.getSharedPreferences(SHARED_PREF_NAME,context1.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL,null);

    }
    public String getImage(){
        SharedPreferences sharedPreferences=context1.getSharedPreferences(SHARED_PREF_NAME,context1.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_image,null);

    }
    public int getGroupId(){
        SharedPreferences sharedPreferences=context1.getSharedPreferences(SHARED_PREF_NAME,context1.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_GROUP_ID,0);

    }
    public int getId(){
        SharedPreferences sharedPreferences=context1.getSharedPreferences(SHARED_PREF_NAME,context1.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_ID,0);

    }




}

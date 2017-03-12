package com.whrsmxmx.maxim_adressbook;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Max on 12.03.2017.
 */

public class LoginManager {
//    TODO: USE OAUTH OR ENCRYPTION.

    public static final String PREF = "PREF";
    public static final String NAME = "NAME";
    public static final String PASS = "PASS";

    private String mName;
    private String mPass;
    private boolean isLoggiedIn;
    private SharedPreferences mSharedPreferences;

    LoginManager(Context context){
        mSharedPreferences = context.getSharedPreferences(PREF, MODE_PRIVATE);
        if(mSharedPreferences.contains(NAME)){
            mName = mSharedPreferences.getString(NAME, "");
            mPass = mSharedPreferences.getString(PASS, "");
            isLoggiedIn = true;
        }
    }

    public void set(String name, String pass){
        mSharedPreferences.edit()
                .putString(NAME, name)
                .putString(PASS, pass)
                .apply();
        isLoggiedIn = true;
    }

    public String getName() {
        return mName;
    }

    public String getPass() {
        return mPass;
    }

    public boolean isLoggiedIn() {
        return isLoggiedIn;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setPass(String pass) {
        mPass = pass;
    }

    public void setLoggiedIn(boolean loggiedIn) {
        isLoggiedIn = loggiedIn;
    }

    public void logout() {
        mSharedPreferences.edit().remove(NAME).remove(PASS).apply();
        isLoggiedIn = false;
    }
}

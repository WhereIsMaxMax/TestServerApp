package com.whrsmxmx.maxim_adressbook;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Max on 10.03.2017.
 */

public class App extends Application {

    private static TaxiService mTaxiService;

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mTaxiService = retrofit.create(TaxiService.class);
    }

    public static TaxiService getApi(){
        return mTaxiService;
    }

    public static String CODE_EMPLOYEE = "EMPLOYEE";
}

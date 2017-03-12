package com.whrsmxmx.maxim_adressbook;

import com.whrsmxmx.maxim_adressbook.model.AuthResponse;
import com.whrsmxmx.maxim_adressbook.model.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Max on 10.03.2017.
 */

public interface TaxiService {
    @GET("/Contacts.svc/Hello?")
    Call<AuthResponse> getAuth(@Query("login") String login, @Query("password") String path);

    @GET("/Contacts.svc/GetAll?")
    Call<Example> getDepartments(@Query("login") String login, @Query("password") String path);
}
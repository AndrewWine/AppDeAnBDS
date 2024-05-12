package com.example.test;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface RetrofitAPI {


    @GET
    Call<MsgModal> getMessage(@Url String message);


    @POST
    Call<MsgModal> postMessage(@Url String message);
}
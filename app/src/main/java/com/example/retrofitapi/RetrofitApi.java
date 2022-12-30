package com.example.retrofitapi;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitApi {

    @POST("api/users/2")
    Call<DataModel> createPost(@Body DataModel dataModel);
}
package com.example.foodline.network;

import com.example.foodline.model.DefaultResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FoodApi {

    @FormUrlEncoded
    @POST("/api/users/registeruser/")
    Call<DefaultResponse> register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/api/users/login/")
    Call<DefaultResponse> login(
            @Field("username") String email,
            @Field("password") String password
    );
}

package com.example.apitesting;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

  @GET("values.json")
    Call<Response> getPost();

}

package com.shahpar.retrofittraining.apimanager;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestApi {

    @GET("todos/1")
    Call<ApiResponse> getTodo();

}

package com.shahpar.retrofittraining.apimanager;

import android.util.Log;

import com.shahpar.retrofittraining.executer.CallBackApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestManager {

    RequestApi requestApi;

    public RequestManager() {
        requestApi = RetrofitClient.getInstance().create(RequestApi.class);
    }

    public void getTodos(CallBackApi<ApiResponse> callBackApi) {
        Log.d("SHAHPAR", "I am here");
        Call<ApiResponse> response = requestApi.getTodo();
        response.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.d("SHAHPAR", "I am here 2" + response.raw());
                callBackApi.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                callBackApi.onException(t.getMessage());
            }
        });
    }
}

package com.shahpar.retrofittraining.executer;

import com.shahpar.retrofittraining.apimanager.ApiResponse;
import com.shahpar.retrofittraining.apimanager.RequestApi;
import com.shahpar.retrofittraining.apimanager.RequestManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskExecutor {

    ExecutorService executorService;
    RequestManager requestManager;

    public TaskExecutor() {
        executorService = Executors.newSingleThreadExecutor();
        requestManager = new RequestManager();
    }

    public void getTodos(CallBackApi<ApiResponse> callBackApi) {
        executorService.execute(() -> {
            requestManager.getTodos(callBackApi);
        });
    }
}

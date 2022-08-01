package com.shahpar.retrofittraining.executer;

public interface CallBackApi<T> {

    void onResponse(T response);

    void onException(String error);
}

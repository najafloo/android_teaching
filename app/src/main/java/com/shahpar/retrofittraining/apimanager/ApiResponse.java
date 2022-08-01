package com.shahpar.retrofittraining.apimanager;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {

    @SerializedName("userId")
    private int userId;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("completed")
    private boolean compelete;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompelete() {
        return compelete;
    }
}

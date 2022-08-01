package com.shahpar.retrofittraining;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.shahpar.retrofittraining.apimanager.ApiResponse;
import com.shahpar.retrofittraining.executer.CallBackApi;
import com.shahpar.retrofittraining.executer.TaskExecutor;

public class MainActivity extends AppCompatActivity {

    TaskExecutor taskExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView txt_textview = findViewById(R.id.txt_textview);
        taskExecutor = new TaskExecutor();

        taskExecutor.getTodos(new CallBackApi<ApiResponse>() {
            @Override
            public void onResponse(ApiResponse response) {
                runOnUiThread(() -> {
                    Log.d("SHAHPAR", response.getTitle());
                    txt_textview.setText(response.getTitle());
                });
            }

            @Override
            public void onException(String error) {

            }
        });
    }
}
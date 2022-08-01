package com.shahpar.mvvmtraining.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import com.shahpar.mvvmtraining.R;
import com.shahpar.mvvmtraining.databinding.ActivityMainBinding;
import com.shahpar.mvvmtraining.viewmodels.LoginViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        activityMainBinding.setViewModel(new LoginViewModel());
        activityMainBinding.executePendingBindings();
    }

    @BindingAdapter({"toastMessage"})
    public static void runMe(View view, String message) {
        if(message != null)
            Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();

        Log.d("SHAHPAR", "helloooooooo" + message);

    }
//
//    @BindingAdapter({"myVar"})
//    public static void runMe(View view, String message) {
//        Log.d("SHAHPAR", "helloooooooo" + message);
//    }
}
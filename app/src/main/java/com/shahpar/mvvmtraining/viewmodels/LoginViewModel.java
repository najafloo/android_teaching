package com.shahpar.mvvmtraining.viewmodels;


import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.shahpar.mvvmtraining.BR;
import com.shahpar.mvvmtraining.model.User;

public class LoginViewModel extends BaseObservable {
    private User user;
    private String successMessage = "Login was successful";
    private String errorMessage = "Email or Password not valid";

    public LoginViewModel() {
        this.user = new User("","");
    }

    public String getMyVar() {
        return myVar;
    }

    public void setMyVar(String myVar) {
        this.myVar = myVar;
        Log.d("SHAHPAR","my variable = " + myVar);
        notifyPropertyChanged(BR.myVar);
    }

    @Bindable
    private String myVar = null;

    @Bindable
    private String toastMessage = null;

    public String getToastMessage() {
        return toastMessage;
    }

    private void setToastMessage(String toastMessage) {
        this.toastMessage = toastMessage;
        notifyPropertyChanged(BR.toastMessage);
    }

    public void setUserEmail(String userEmail) {
        Log.d("SHAHPAR", userEmail);

        user.setEmail(userEmail);
        notifyPropertyChanged(BR.userEmail);
    }

    public void setUserPassword(String userPassword) {
        user.setPassword(userPassword);
        notifyPropertyChanged(BR.userPassword);
    }

    @Bindable
    public String getUserEmail() {
        return user.getEmail();
    }

    @Bindable
    public String getUserPassword() {
        return user.getPassword();
    }

    public void onLoginClicked() {
        if(isInputDataIsValid())
            setToastMessage(successMessage);
        else
            setToastMessage(errorMessage);

    }

    public void showVariable() {
        Log.d("SHAHPAR","I am clicked");

        setMyVar("56546546");
    }

    public boolean isInputDataIsValid() {
        return !TextUtils.isEmpty(getUserEmail()) &&
                Patterns.EMAIL_ADDRESS.matcher(getUserEmail()).matches() &&
                !TextUtils.isEmpty(getUserPassword()) &&
                getUserPassword().length() > 5;
    }
}

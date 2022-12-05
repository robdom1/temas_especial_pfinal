package com.example.proyecto_final.ui.profile;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel

    private final MutableLiveData<String> mText;

    public ProfileViewModel(Application application){
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is Profile fragment");
    }
    public LiveData<String> getText(){ return mText; }
}
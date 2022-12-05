package com.example.proyecto_final.ui.help;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HelpViewModel extends AndroidViewModel {
    private final MutableLiveData<String> mText;

    public HelpViewModel(Application application){
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is help fragment");
    }

    public LiveData<String> getText() { return mText; }
}
package com.jz_jec_g01.tripwiz.ui.guide;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GuideViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GuideViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
package com.jz_jec_g01.tripwiz.ui.talk.Chats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TalkViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TalkViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is notifications fragment");
//        TalkViewModel.init(messages);
    }


    public LiveData<String> getText() {
        return mText;
    }
}
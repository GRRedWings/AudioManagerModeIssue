package com.example.audiotestapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TTSViewModel extends ViewModel {

   private final MutableLiveData<Boolean> isSpeaking = new MutableLiveData<>();

   public void toggleSpeaking(Boolean newState ) {
      isSpeaking.setValue(newState);
   }

   public LiveData<Boolean> getIsSpeaking() {
      return isSpeaking;
   }

}

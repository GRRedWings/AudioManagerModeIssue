package com.example.audiotestapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TTSViewModel extends ViewModel {

   private final MutableLiveData<Boolean> isSpeaking = new MutableLiveData<>();

   private final MutableLiveData<Integer> issueCount = new MutableLiveData<>(0);


   public void toggleSpeaking(Boolean newState ) {
      isSpeaking.setValue(newState);
   }

   public LiveData<Boolean> getIsSpeaking() {
      return isSpeaking;
   }

   public void incrementIssueCount() {

      if (issueCount == null) {
         issueCount.setValue(1);
      }
      else {
         int curCount = issueCount.getValue();
         issueCount.setValue(curCount +1);
      }
   }

   public LiveData<Integer> getIssueCount() { return issueCount; }
}

package com.example.audiotestapp;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.audiotestapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

   private AppBarConfiguration appBarConfiguration;
   private ActivityMainBinding binding;
   private TTSViewModel ttsViewModel;

   private Boolean ttsEnabled = false;
   // This sets us up to use the Bluetooth Audio
   private static AudioManager am;
   private static final int expectedMode = AudioManager.MODE_IN_COMMUNICATION;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // Setup our View Model so we know what is being asked of us
      ttsViewModel = new ViewModelProvider(this).get(TTSViewModel.class);
      ttsViewModel.toggleSpeaking(false);
      ttsViewModel.getIsSpeaking().observe(this, curStatus -> {
         // Toggle our internal flag to start / stop speaking
         ttsEnabled = curStatus;
      });

      startTtsThread();

      // Setup our Audio Manager
      am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
      am.setMode(expectedMode);
      am.startBluetoothSco();

      binding = ActivityMainBinding.inflate(getLayoutInflater());
      setContentView(binding.getRoot());

      setSupportActionBar(binding.toolbar);

      NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
      appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
      NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu_main, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
      int id = item.getItemId();

      //noinspection SimplifiableIfStatement
      if (id == R.id.action_settings) {
         return true;
      }

      return super.onOptionsItemSelected(item);
   }

   @Override
   public boolean onSupportNavigateUp() {
      NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
      return NavigationUI.navigateUp(navController, appBarConfiguration)
            || super.onSupportNavigateUp();
   }

   private void startTtsThread() {

      Thread ttsThread = new Thread(() -> {
         String TAG = "TTS Thread";
         TextToSpeech tts = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
               ;
            }
         });

         Bundle lParams = new Bundle();
         lParams.putInt(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_MUSIC);

         lParams.putInt(TextToSpeech.Engine.KEY_PARAM_STREAM,
               AudioManager.STREAM_VOICE_CALL);

         // Just loop forever
         while (true) {

            if(am.getMode() != expectedMode) {
               Log.e(TAG, "Mode is not as expected.  TTS Enabled is " + ttsEnabled);

               runOnUiThread(() -> {
                  ttsViewModel.incrementIssueCount();

               });
               am.setMode(expectedMode);
            }

            // If we are told to speak, speak
            if (ttsEnabled) {
               tts.speak("We are talking to you", TextToSpeech.QUEUE_FLUSH, lParams, "");
            }

            // Sleep a little while
            try {
               Thread.sleep(2500);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      });

      ttsThread.setName("TTS Thread");
      ttsThread.start();
   }
}
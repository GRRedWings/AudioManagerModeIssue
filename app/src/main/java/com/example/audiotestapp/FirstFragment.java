package com.example.audiotestapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.example.audiotestapp.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

   private FragmentFirstBinding binding;
   private TTSViewModel ttsViewModel;

   private final int messageString = R.string.hello_first_fragment;
   ViewGroup curViewGroup;
   private TextView titleView;

   @Override
   public View onCreateView(
         LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState
   ) {

      curViewGroup = container;

      binding = FragmentFirstBinding.inflate(inflater, container, false);
      return binding.getRoot();
   }

   public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);

      ttsViewModel = new ViewModelProvider(requireActivity()).get(TTSViewModel.class);

      if (curViewGroup != null && getActivity() != null) {
         titleView = curViewGroup.findViewById(R.id.textview_first);

         ttsViewModel.getIssueCount().observe(getActivity(), curIssueCount -> {
            // Toggle our internal flag to start / stop speaking
            String newText = getString(messageString, String.valueOf(curIssueCount));
            titleView.setText(newText);
         });
      }

      binding.buttonFirst.setOnClickListener(view1 -> {
         ttsViewModel.toggleSpeaking(true);
         NavHostFragment.findNavController(FirstFragment.this)
               .navigate(R.id.action_FirstFragment_to_SecondFragment);
      });
   }

   @Override
   public void onDestroyView() {
      super.onDestroyView();
      binding = null;
   }
}
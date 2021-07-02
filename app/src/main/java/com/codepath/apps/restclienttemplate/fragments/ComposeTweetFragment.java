package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComposeTweetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComposeTweetFragment extends DialogFragment {

    public static final int MAX_CHARS = 280;

    EditText etComposeF;

    TextView tvCharactersRemaining;
    TextView tvNameF;
    TextView tvScreenNameF;

    Button btnTweetF;
    Button btnCancel;

    ImageView profileImageF;

    public ComposeTweetFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }



    public static ComposeTweetFragment newInstance(String title) {
        ComposeTweetFragment frag = new ComposeTweetFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }



    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose_tweet, container);
    }



    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etComposeF = view.findViewById(R.id.etComposeF);
        tvCharactersRemaining = view.findViewById(R.id.etComposeF);
        tvNameF = view.findViewById(R.id.etComposeF);
        tvScreenNameF = view.findViewById(R.id.etComposeF);
        btnTweetF = view.findViewById(R.id.etComposeF);
        btnCancel = view.findViewById(R.id.etComposeF);
        profileImageF = view.findViewById(R.id.etComposeF);

        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);

        // Show soft keyboard automatically and request focus to field

        etComposeF.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
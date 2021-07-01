package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailsAcitivity extends AppCompatActivity {

    Long id;
    TextView tvBody;
    TextView tvScreenName;
    TextView tvName;
    ImageView ivProfileImage;
    ImageView ivMedia;

    ImageButton btnLike;
    ImageButton btnRetweet;
    ImageButton btnReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_acitivity);

        getSupportActionBar().setTitle("Tweet Details");

        id = getIntent().getLongExtra("id", 0);

        tvBody = findViewById(R.id.tvBody);
        tvScreenName = findViewById(R.id.tvName);
        tvName = findViewById(R.id.tvScreenName);

        ivProfileImage = findViewById(R.id.ivProfileImage);
        ivMedia = findViewById(R.id.ivMedia);

        btnLike = findViewById(R.id.btnLike);
        btnRetweet = findViewById(R.id.btnRetweet);
        btnReply = findViewById(R.id.btnReply);

        tvBody.setText(getIntent().getStringExtra("body"));
        tvScreenName.setText(getIntent().getStringExtra("screen_name"));
        tvName.setText(getIntent().getStringExtra("name"));

        Glide.with(this)
                .load(getIntent().getStringExtra("profile_image"))
                .into(ivProfileImage);
        Glide.with(this)
                .load(getIntent().getStringExtra("media_url"))
                .into(ivMedia);


    }
}
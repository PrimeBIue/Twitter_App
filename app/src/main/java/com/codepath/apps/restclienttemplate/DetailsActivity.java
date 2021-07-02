package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import okhttp3.Headers;

public class DetailsActivity extends AppCompatActivity {

    public static final String TAG = "DetailsActivity";

    TwitterClient client;

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

        client = TwitterApp.getRestClient(this);

        setContentView(R.layout.activity_details_acitivity);

        getSupportActionBar().setTitle("Tweet Details");

        id = getIntent().getLongExtra("id", 0);

        tvBody = findViewById(R.id.tvBody);
        tvScreenName = findViewById(R.id.tvName);
        tvName = findViewById(R.id.tvScreenName);

        ivProfileImage = findViewById(R.id.ivProfileImageF);
        ivMedia = findViewById(R.id.ivMedia);

        btnLike = findViewById(R.id.btnLike);
        btnRetweet = findViewById(R.id.btnRetweet);
        btnReply = findViewById(R.id.btnReply);

        tvBody.setText(getIntent().getStringExtra("body"));
        tvScreenName.setText("@" + getIntent().getStringExtra("screen_name"));
        tvName.setText(getIntent().getStringExtra("name"));

        Glide.with(this)
                .load(getIntent().getStringExtra("profile_image"))
                .into(ivProfileImage);
        Glide.with(this)
                .load(getIntent().getStringExtra("media_url"))
                .into(ivMedia);

        // Like button onclicklistener
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.likeTweet(id, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        btnLike.setColorFilter(Color.RED);
                        Log.i(TAG, "onSuccess liking tweet: " + id);
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        client.unlikeTweet(id, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Headers headers, JSON json) {
                                Log.i(TAG, "onSuccess unliking tweet");
                                btnLike.setColorFilter(Color.BLACK);
                            }

                            @Override
                            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                                Log.i(TAG, "onFailure liking/unliking tweet");
                            }
                        });
                    }
                });
            }
        });

        // Retweet button onclicklistener
        btnRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.retweetTweet(id, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        btnRetweet.setColorFilter(Color.GREEN);
                        Log.i(TAG, "onSuccess retweeting tweet: " + id);
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.i(TAG, "onFailure retweeting tweet");
                    }
                });
            }
        });

        // Reply button onclicklistener
        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnReply.setColorFilter(Color.BLUE);
                Intent intent = new Intent(DetailsActivity.this, ComposeActivity.class);
                intent.putExtra("screen_name", getIntent().getStringExtra("screen_name"));
                intent.putExtra("id", id);
                startActivityForResult(intent, 21);
            }
        });
    }
}
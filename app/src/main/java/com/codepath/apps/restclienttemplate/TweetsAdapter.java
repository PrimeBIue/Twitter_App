package com.codepath.apps.restclienttemplate;

import android.content.Context;

import android.net.ParseException;
import android.os.Build;


import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{

    public interface OnClickListener {
        void onBtnLikeClicked(int position);
        void onBtnReplyClicked(int position);
        void onBtnRetweetClicked(int position);
    }

    Context context;
    List<Tweet> tweets;
    OnClickListener clickListener;
    // Pass in context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets, OnClickListener clickListener) {
        this.clickListener = clickListener;
        this.context = context;
        this.tweets = tweets;
    }

    // For each row, inflate a layout
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);

    }

    // Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        // get data at position
        Tweet tweet = tweets.get(position);
        // bind the tweet with the view holder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }




    // Define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvRelativeTime;
        ImageView ivMedia;
        ImageButton btnLike;
        ImageButton btnRetweet;
        ImageButton btnReply;
        TextView tvName;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            btnLike = itemView.findViewById(R.id.btnLike);
            btnRetweet = itemView.findViewById(R.id.btnRetweet);
            btnReply = itemView.findViewById(R.id.btnReply);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvRelativeTime = itemView.findViewById(R.id.tvRelativeTime);
            ivMedia = itemView.findViewById(R.id.ivMedia);
            tvName = itemView.findViewById(R.id.tvName);
        }


        public void bind(Tweet tweet) {

            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop
            Log.i("Adapter", tweet.body);
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.name);
            tvRelativeTime.setText(getRelativeTimeAgo(tweet.createdAt));
            tvName.setText("@" + tweet.user.screenName);
            Glide.with(context)
                    .load(tweet.user.profileImageUrl)
                    .into(ivProfileImage);

            if (tweet.entities.mediaUrl == null) {
                ivMedia.setVisibility(View.GONE);
            } else {
                ivMedia.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(tweet.entities.mediaUrl)
                        .transform(new RoundedCornersTransformation(radius+20, margin))
                        .into(ivMedia);
            }

            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { clickListener.onBtnLikeClicked(getAdapterPosition()); }
            });

            btnReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { clickListener.onBtnReplyClicked(getAdapterPosition()); }
            });

            btnRetweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { clickListener.onBtnRetweetClicked(getAdapterPosition()); }
            });
        }
    }
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = getAbbreviatedTimeSpan(dateMillis);
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public static String getAbbreviatedTimeSpan(long timeMillis) {
        long span = Math.max(System.currentTimeMillis() - timeMillis, 0);
        if (span >= DateUtils.YEAR_IN_MILLIS) {
            return (span / DateUtils.YEAR_IN_MILLIS) + "y";
        }
        if (span >= DateUtils.WEEK_IN_MILLIS) {
            return (span / DateUtils.WEEK_IN_MILLIS) + "w";
        }
        if (span >= DateUtils.DAY_IN_MILLIS) {
            return (span / DateUtils.DAY_IN_MILLIS) + "d";
        }
        if (span >= DateUtils.HOUR_IN_MILLIS) {
            return (span / DateUtils.HOUR_IN_MILLIS) + "h";
        }
        if (span >= DateUtils.MINUTE_IN_MILLIS) {
            return (span / DateUtils.MINUTE_IN_MILLIS) + "m";
        }
        return (span / DateUtils.SECOND_IN_MILLIS) + "s";
    }

    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addALl(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }
}



package com.example.deepamgoel.amplifire.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.deepamgoel.amplifire.R;
import com.example.deepamgoel.amplifire.fragments.NowPlayingFragment;
import com.example.deepamgoel.amplifire.models.Media;

public class NowPlayingActivity extends AppCompatActivity {

    private Media media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);
        if (savedInstanceState != null)
            Log.d("TAG", "SaveInstanceState: " + String.valueOf(savedInstanceState));


        NowPlayingFragment fragment =
                (NowPlayingFragment) getSupportFragmentManager().
                        findFragmentById(R.id.fragment_now_playing);

        Intent intent = getIntent();
        if (savedInstanceState != null)
            media = savedInstanceState.getParcelable("media");
        else
            media = intent.getParcelableExtra("media");

        if (media != null)
            fragment.changeData(media);
        else
            fragment.changeData(new Media(getApplicationContext(), R.raw.starboy));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("media", media);
        Log.d("TAG", "onSaveInstanceState: ");
    }
}

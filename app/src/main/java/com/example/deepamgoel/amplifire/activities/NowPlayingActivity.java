package com.example.deepamgoel.amplifire.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.deepamgoel.amplifire.R;
import com.example.deepamgoel.amplifire.fragments.NowPlayingFragment;
import com.example.deepamgoel.amplifire.models.Song;

public class NowPlayingActivity extends AppCompatActivity {

    private Song song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        NowPlayingFragment fragment =
                (NowPlayingFragment) getSupportFragmentManager().
                        findFragmentById(R.id.fragment_now_playing);

        Intent intent = getIntent();
        if (savedInstanceState != null)
            song = savedInstanceState.getParcelable("song");
        else
            song = intent.getParcelableExtra("song");

        if (song != null)
            fragment.changeData(song);
        else
            fragment.changeData(new Song(getApplicationContext(), R.raw.starboy));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("song", song);
    }
}

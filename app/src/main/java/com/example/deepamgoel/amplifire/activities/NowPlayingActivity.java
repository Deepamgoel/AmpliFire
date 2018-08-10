package com.example.deepamgoel.amplifire.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.deepamgoel.amplifire.R;
import com.example.deepamgoel.amplifire.fragments.NowPlayingFragment;
import com.example.deepamgoel.amplifire.models.Song;

public class NowPlayingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        NowPlayingFragment fragment =
                (NowPlayingFragment) getSupportFragmentManager().
                        findFragmentById(R.id.fragment_now_playing);

        Song song;
        Intent intent = getIntent();
        if (intent.hasExtra("song"))
            song = intent.getParcelableExtra("song");
        else
            song = new Song(getApplicationContext(), R.raw.heartless);

        if (song != null)
            fragment.changeData(song);
    }
}
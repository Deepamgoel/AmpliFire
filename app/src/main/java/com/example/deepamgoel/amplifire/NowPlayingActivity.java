package com.example.deepamgoel.amplifire;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fragments.NowPlayingFragment;

public class NowPlayingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        NowPlayingFragment fragment =
                (NowPlayingFragment) getSupportFragmentManager().
                        findFragmentById(R.id.fragment_now_playing);

        Intent intent = getIntent();
        if (fragment != null) {
            fragment.changeData(intent.getIntExtra("id", R.raw.animals));
        }
    }
}

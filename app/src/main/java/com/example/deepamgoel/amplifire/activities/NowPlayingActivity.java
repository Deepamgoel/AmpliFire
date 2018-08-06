package com.example.deepamgoel.amplifire.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.deepamgoel.amplifire.R;
import com.example.deepamgoel.amplifire.fragments.NowPlayingFragment;

public class NowPlayingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        NowPlayingFragment fragment =
                (NowPlayingFragment) getSupportFragmentManager().
                        findFragmentById(R.id.fragment_now_playing);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", R.raw.animals);
        fragment.changeData(id);
    }
}

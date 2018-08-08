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
//        if (savedInstanceState != null) {
//            nowPlayingFragment = (NowPlayingFragment) getSupportFragmentManager().
//                    getFragment(savedInstanceState, "nowPlayingFragment");
//        } else {
//        }

//        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        nowPlayingFragment = new NowPlayingFragment();
//        fragmentTransaction.replace(R.id.fragment_now_playing_container,
//                nowPlayingFragment, "now_playing_fragment");
//        fragmentTransaction.commit();

        NowPlayingFragment fragment =
                (NowPlayingFragment) getSupportFragmentManager().
                        findFragmentById(R.id.fragment_now_playing);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", R.raw.animals);
        fragment.changeData(id);
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        Log.d("TAG", "onSaveInstanceState: NowPlayingActivity");
//
//        getSupportFragmentManager().putFragment(
//                outState,
//                "nowPlayingFragment",
//                getSupportFragmentManager().findFragmentByTag("now_playing_fragment"));
//    }
}

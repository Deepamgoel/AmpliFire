package com.example.deepamgoel.amplifire;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import fragments.NowPlayingFragment;
import fragments.PlaylistFragment;
import fragments.SongsFragment;
import fragments.SongsListFragment;
import utils.ListItemSongs;

public class MainActivity extends AppCompatActivity implements SongsListFragment.Communicator {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.bnv)
    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loadFragment(new SongsFragment());
        Log.d("TAG", "SongsFragment loaded");
        bnv.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment;
                        switch (item.getItemId()) {
                            case R.id.navigation_songs:
                                fragment = new SongsFragment();
                                loadFragment(fragment);
                                return true;

                            case R.id.navigation_playlist:
                                fragment = new PlaylistFragment();
                                loadFragment(fragment);
                                return true;

                        }
                        return false;
                    }
                });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NowPlayingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void respond(ListItemSongs song) {
        NowPlayingActivity activity = new NowPlayingActivity();
        NowPlayingFragment fragment =
                (NowPlayingFragment) activity.getSupportFragmentManager().findFragmentById(R.id.fragment_now_playing);
        Log.d("Fragment", String.valueOf(fragment));
//        fragment.changeData(song);
    }
}

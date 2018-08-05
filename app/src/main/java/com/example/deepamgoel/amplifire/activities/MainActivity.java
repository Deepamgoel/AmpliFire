package com.example.deepamgoel.amplifire.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.deepamgoel.amplifire.R;
import com.example.deepamgoel.amplifire.fragments.BnvPlaylistFragment;
import com.example.deepamgoel.amplifire.fragments.BnvSongsFragment;
import com.example.deepamgoel.amplifire.fragments.SongsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SongsFragment.Communicator {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.bnv)
    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loadFragment(new BnvSongsFragment());
        bnv.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment;
                        switch (item.getItemId()) {
                            case R.id.navigation_songs:
                                fragment = new BnvSongsFragment();
                                loadFragment(fragment);
                                return true;

                            case R.id.navigation_playlist:
                                fragment = new BnvPlaylistFragment();
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
    public void respond(int id) {
        Intent intent = new Intent(this, NowPlayingActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}

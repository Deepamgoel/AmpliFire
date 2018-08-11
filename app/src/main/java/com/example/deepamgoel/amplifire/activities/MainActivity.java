package com.example.deepamgoel.amplifire.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.deepamgoel.amplifire.R;
import com.example.deepamgoel.amplifire.fragments.AlbumFragment;
import com.example.deepamgoel.amplifire.fragments.ArtistFragment;
import com.example.deepamgoel.amplifire.fragments.BnvPlaylistFragment;
import com.example.deepamgoel.amplifire.fragments.BnvSongsFragment;
import com.example.deepamgoel.amplifire.fragments.SongsFragment;
import com.example.deepamgoel.amplifire.models.Song;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        SongsFragment.CallbackSongs,
        ArtistFragment.CallbackArtist,
        AlbumFragment.CallbackAlbum {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.bnv)
    BottomNavigationView bnv;

    public static List<Song> addData(View view) {
        List<Song> list = new ArrayList<>();
        Song item;

        item = new Song(view.getContext(), R.raw.starboy);
        list.add(item);

        item = new Song(view.getContext(), R.raw.animals);
        list.add(item);

        item = new Song(view.getContext(), R.raw.forgiveness);
        list.add(item);

        item = new Song(view.getContext(), R.raw.better);
        list.add(item);

        item = new Song(view.getContext(), R.raw.burn);
        list.add(item);

        item = new Song(view.getContext(), R.raw.heartless);
        list.add(item);

        item = new Song(view.getContext(), R.raw.human);
        list.add(item);

        item = new Song(view.getContext(), R.raw.party_hard_chris_brown);
        list.add(item);

        item = new Song(view.getContext(), R.raw.when_the_bassline_drops);
        list.add(item);

        item = new Song(view.getContext(), R.raw.champagne_problems);
        list.add(item);

        return list;
    }

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

        fab.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.play_circle_outline));
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
    public void respondSongs(Song song) {
        Intent intent = new Intent(this, NowPlayingActivity.class);
        intent.putExtra("song", song);
        startActivity(intent);
    }

    @Override
    public void respondArtist(Song song) {

    }

    @Override
    public void respondAlbum(Song song) {

    }

}

package com.example.deepamgoel.amplifire.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deepamgoel.amplifire.R;
import com.example.deepamgoel.amplifire.adapters.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BnvSongsFragment extends Fragment {

    @BindView(R.id.toolbar_bnv_songs_fragment)
    Toolbar toolbar;
    @BindView(R.id.tabs_bnv_songs_fragment)
    TabLayout tabLayout;
    @BindView(R.id.viewpager_bnv_songs_fragment)
    ViewPager viewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bnv_songs, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ArtistFragment());
        adapter.addFragment(new AlbumFragment());
        adapter.addFragment(new SongsFragment());
        viewPager.setAdapter(adapter);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.person);
        tabLayout.getTabAt(1).setIcon(R.drawable.album);
        tabLayout.getTabAt(2).setIcon(R.drawable.songs);
    }
}

package fragments.bnv;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deepamgoel.amplifire.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import fragments.songs.AlbumFragment;
import fragments.songs.ArtistFragment;
import fragments.songs.SongsListFragment;
import utils.ViewPagerAdapter;

public class SongsFragment extends Fragment {

    @BindView(R.id.toolbar_songs_fragment)
    Toolbar toolbar;
    @BindView(R.id.tabs_songs_fragment)
    TabLayout tabLayout;
    @BindView(R.id.viewpager_songs_fragment)
    ViewPager viewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_songs, container, false);
        ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ArtistFragment());
        adapter.addFragment(new AlbumFragment());
        adapter.addFragment(new SongsListFragment());
        viewPager.setAdapter(adapter);
        setupTabIcons();

        return view;
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.person);
        tabLayout.getTabAt(1).setIcon(R.drawable.album);
        tabLayout.getTabAt(2).setIcon(R.drawable.songs);
    }
}

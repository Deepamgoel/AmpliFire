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
import fragments.playlist.FavoriteFragment;
import fragments.playlist.PlaylistListFragment;
import utils.ViewPagerAdapter;

public class PlaylistFragment extends Fragment {

    @BindView(R.id.toolbar_playlist_fragment)
    Toolbar toolbar;
    @BindView(R.id.tabs_playlist_fragment)
    TabLayout tabLayout;
    @BindView(R.id.viewpager_playlist_fragment)
    ViewPager viewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PlaylistListFragment());
        adapter.addFragment(new FavoriteFragment());
        viewPager.setAdapter(adapter);
        setupTabIcons();

        return view;
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.playlist_play);
        tabLayout.getTabAt(1).setIcon(R.drawable.heart);
    }
}

package com.example.deepamgoel.amplifire.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deepamgoel.amplifire.R;
import com.example.deepamgoel.amplifire.adapters.ArtistAdapter;
import com.example.deepamgoel.amplifire.models.Song;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.deepamgoel.amplifire.activities.MainActivity.addData;

public class ArtistFragment extends Fragment {

    @BindView(R.id.recycler_view_artist)
    RecyclerView recyclerView;

    CallbackArtist callbackArtist;
    View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CallbackArtist) {
            callbackArtist = (CallbackArtist) context;
        } else {
            throw new RuntimeException(getContext().toString()
                    + " must implement CallbackSongs");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_artist, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Song> list = addData(view);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        recyclerView.setAdapter(new ArtistAdapter(view.getContext(), list, callbackArtist));
    }


    public interface CallbackArtist {
        void respondArtist(Song song);
    }
}
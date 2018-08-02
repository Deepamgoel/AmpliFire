package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deepamgoel.amplifire.R;
import com.example.deepamgoel.amplifire.SongsListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import utils.Media;

public class SongsListFragment extends Fragment {

    @BindView(R.id.recycler_view_songs_list)
    RecyclerView recyclerView;

    List<Media> list;
    Communicator communicator;
    View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Communicator) {
            communicator = (Communicator) context;
        } else {
            throw new RuntimeException(getContext().toString()
                    + " must implement Communicator");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_songs_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        addData();
        recyclerView.setAdapter(new SongsListAdapter(view.getContext(), list, communicator));
    }

    private void addData() {
        Media item;

        item = new Media(view.getContext(), R.raw.animals);
        list.add(item);

        item = new Media(view.getContext(), R.raw.better);
        list.add(item);

        item = new Media(view.getContext(), R.raw.burn);
        list.add(item);

        item = new Media(view.getContext(), R.raw.cant_stop_the_feeling);
        list.add(item);

        item = new Media(view.getContext(), R.raw.champagne_problems);
        list.add(item);

        item = new Media(view.getContext(), R.raw.dna);
        list.add(item);

        item = new Media(view.getContext(), R.raw.forgiveness);
        list.add(item);

        item = new Media(view.getContext(), R.raw.heartless);
        list.add(item);

        item = new Media(view.getContext(), R.raw.human);
        list.add(item);

        item = new Media(view.getContext(), R.raw.hymn_for_the_weekend);
        list.add(item);

        item = new Media(view.getContext(), R.raw.im_not_theonly_one);
        list.add(item);

        item = new Media(view.getContext(), R.raw.love_yourself);
        list.add(item);

        item = new Media(view.getContext(), R.raw.move_your_body);
        list.add(item);

        item = new Media(view.getContext(), R.raw.party_hard_chris_brown);
        list.add(item);

        item = new Media(view.getContext(), R.raw.starboy);
        list.add(item);

        item = new Media(view.getContext(), R.raw.when_the_bassline_drops);
        list.add(item);

        item = new Media(view.getContext(), R.raw.years_seven);
        list.add(item);

        item = new Media(view.getContext(), R.raw.yellow);
        list.add(item);
    }

    public interface Communicator {
        void respond(int id);
    }
}
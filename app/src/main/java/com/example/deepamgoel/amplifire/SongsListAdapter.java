package com.example.deepamgoel.amplifire;

import android.media.MediaMetadataRetriever;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import utils.ListItemSongs;

public class SongsListAdapter extends RecyclerView.Adapter<SongsListAdapter.ViewHolder> {

    private List<ListItemSongs> list;

    public SongsListAdapter(List<ListItemSongs> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_songs,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItemSongs song = list.get(position);
        MediaMetadataRetriever metadataRetriever = song.getMetadataRetriever();

        String title = metadataRetriever.extractMetadata(
                MediaMetadataRetriever.METADATA_KEY_TITLE);
        String artist = metadataRetriever.extractMetadata(
                MediaMetadataRetriever.METADATA_KEY_ARTIST);

        if (title != null)
            holder.title.setText(title);
        else holder.title.setText(R.string.unknown_title);

        if (artist != null)
            holder.artist.setText(artist);
        else holder.artist.setText(R.string.unknown_artist);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView artist;
        ImageButton more;

        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.song_name);
            artist = itemView.findViewById(R.id.song_artist);
            more = itemView.findViewById(R.id.song_more);
            itemView.setOnClickListener(this);
            more.setOnClickListener(this);
        }

        // todo: Implement click listener
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.song_more:
                    break;
                default:
                    break;
            }

        }
    }
}

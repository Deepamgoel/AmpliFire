package com.example.deepamgoel.amplifire.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.deepamgoel.amplifire.R;
import com.example.deepamgoel.amplifire.fragments.SongsFragment;
import com.example.deepamgoel.amplifire.models.Song;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {

    private Context context;
    private List<Song> list;
    private SongsFragment.CallbackSongs callbackSongs;

    public SongsAdapter(Context context, List<Song> list, SongsFragment.CallbackSongs callbackSongs) {
        this.context = context;
        this.list = list;
        this.callbackSongs = callbackSongs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_songs,
                parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Song song = list.get(viewHolder.getAdapterPosition());
                callbackSongs.respondSongs(song);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Song song = list.get(position);
        holder.title.setText(song.getTitle());
        holder.artist.setText(song.getArtist());
        if (!song.getLike())
            dislike(holder, song);
        else if (song.getLike())
            like(holder, song);

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.like.getTag().equals("like"))
                    dislike(holder, song);
                else if (holder.like.getTag().equals("dislike"))
                    like(holder, song);

            }
        });

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void like(ViewHolder holder, Song song) {
        holder.like.setTag("like");
        holder.like.setImageDrawable(context.getResources().getDrawable(R.drawable.heart));
        holder.like.setColorFilter(context.getResources().getColor(R.color.Teal));
        song.setLike(true);
    }

    private void dislike(ViewHolder holder, Song song) {
        holder.like.setTag("dislike");
        holder.like.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_outline));
        holder.like.setColorFilter(context.getResources().getColor(R.color.AliceBlue));
        song.setLike(false);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView artist;
        ImageButton like;
        ImageButton more;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.song_name);
            artist = itemView.findViewById(R.id.song_artist);
            like = itemView.findViewById(R.id.songs_like);
            more = itemView.findViewById(R.id.song_more);
        }
    }
}

package com.example.deepamgoel.amplifire.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.deepamgoel.amplifire.R;
import com.example.deepamgoel.amplifire.fragments.SongsFragment;
import com.example.deepamgoel.amplifire.models.Media;

import java.util.List;


public class SongsListAdapter extends RecyclerView.Adapter<SongsListAdapter.ViewHolder> {

    private Context context;
    private List<Media> list;
    private SongsFragment.Communicator communicator;

    public SongsListAdapter(Context context, List<Media> list, SongsFragment.Communicator communicator) {
        this.context = context;
        this.list = list;
        this.communicator = communicator;
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
                Media media = list.get(viewHolder.getAdapterPosition());
                communicator.respond(media);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Media media = list.get(position);
        holder.title.setText(media.getMediaTitle());
        holder.artist.setText(media.getMediaArtist());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            like.setOnClickListener(this);
            more.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.songs_like:
                    if (like.getTag().equals("off")) {
                        like.setTag("on");
                        like.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.heart));
                        like.setColorFilter(ContextCompat.getColor(context, R.color.Teal));
                    } else if (like.getTag().equals("on")) {
                        like.setTag("off");
                        like.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.heart_outline));
                        like.setColorFilter(ContextCompat.getColor(context, R.color.AliceBlue));
                    }
                    break;

                case R.id.song_more:
                    break;
            }
        }
    }
}

package com.example.deepamgoel.amplifire.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deepamgoel.amplifire.R;
import com.example.deepamgoel.amplifire.fragments.ArtistFragment;
import com.example.deepamgoel.amplifire.models.Song;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {

    private Context context;
    private List<Song> list;
    private ArtistFragment.CallbackArtist callbackArtist;

    public ArtistAdapter(Context context, List<Song> list, ArtistFragment.CallbackArtist callbackArtist) {
        this.context = context;
        this.list = list;
        this.callbackArtist = callbackArtist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_artist,
                parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Song song = list.get(viewHolder.getAdapterPosition());
                callbackArtist.respondArtist(song);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Song song = list.get(position);
        holder.artist.setText(song.getArtist());
        if (song.getArt() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(
                    song.getArt(), 0, song.getArt().length);
            holder.imageView.setImageBitmap(bitmap);
        } else
            holder.imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.default_art));
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

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView artist;
        ImageView imageView;
        ImageButton more;

        ViewHolder(View itemView) {
            super(itemView);
            artist = itemView.findViewById(R.id.artist_name);
            imageView = itemView.findViewById(R.id.artist_image);
            more = itemView.findViewById(R.id.artist_more);
        }
    }
}

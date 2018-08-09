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
import com.example.deepamgoel.amplifire.fragments.AlbumFragment;
import com.example.deepamgoel.amplifire.models.Song;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private Context context;
    private List<Song> list;
    private AlbumFragment.CallbackAlbum callbackAlbum;

    public AlbumAdapter(Context context, List<Song> list, AlbumFragment.CallbackAlbum callbackAlbum) {
        this.context = context;
        this.list = list;
        this.callbackAlbum = callbackAlbum;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_album,
                parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Song song = list.get(viewHolder.getAdapterPosition());
                callbackAlbum.respondAlbum(song);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Song song = list.get(position);
        holder.album.setText(song.getAlbum());
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
        TextView album;
        ImageView imageView;
        ImageButton more;

        ViewHolder(View itemView) {
            super(itemView);
            album = itemView.findViewById(R.id.album_name);
            imageView = itemView.findViewById(R.id.album_image);
            more = itemView.findViewById(R.id.album_more);
        }
    }
}

package com.example.deepamgoel.amplifire.models;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import com.example.deepamgoel.amplifire.R;

import java.util.concurrent.TimeUnit;

public class Media implements Parcelable {

    public static final Parcelable.Creator<Media> CREATOR
            = new Parcelable.Creator<Media>() {
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };
    private Context context;
    private int id;
    private MediaMetadataRetriever metadataRetriever;
    private MediaPlayer mediaPlayer;
    private boolean like;

    public Media(Context context, int id) {
        this.context = context;
        this.id = id;
        setLike(false);
        mediaPlayer = MediaPlayer.create(context, id);
        AssetFileDescriptor afd = context.getResources().openRawResourceFd(id);
        metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
    }

    private Media(Parcel in) {
        /*like = in.readByte() != 0;*/
    }

    public int getId() {
        return id;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public Bitmap getMediaBitmap() {
        try {
            byte[] art = metadataRetriever.getEmbeddedPicture();
            return BitmapFactory.decodeByteArray(art, 0, art.length);
        } catch (Exception e) {
            return null;
        }
    }

    public String getMediaTitle() {
        String title = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        if (title != null)
            return title;
        return context.getString(R.string.unknown_title);
    }

    public String getMediaArtist() {
        String artist = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        if (artist != null)
            return artist;
        return context.getString(R.string.unknown_artist);
    }

    public String getMediaAlbum() {
        String album = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        if (album != null)
            return album;
        return context.getString(R.string.unknown_album);
    }

    public String getMediaDuration() {
        try {
            int time = mediaPlayer.getDuration();
            return (context.getString(R.string.duration,
                    TimeUnit.MILLISECONDS.toMinutes(time),
                    (TimeUnit.MILLISECONDS.toSeconds(time) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)))
            ));
        } catch (Exception e) {
            return context.getString(R.string.duration, 0, 0);
        }
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
        if (like)
            Toast.makeText(context, "Added to favorite", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Removed from favorite", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        /*dest.writeByte((byte) (like ? 1 : 0));*/
    }
}

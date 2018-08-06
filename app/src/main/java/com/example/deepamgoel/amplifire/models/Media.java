package com.example.deepamgoel.amplifire.models;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;

import com.example.deepamgoel.amplifire.R;

import java.util.concurrent.TimeUnit;

public class Media {

    //    public static final Parcelable.Creator<Media> CREATOR
//            = new Parcelable.Creator<Media>() {
//        public Media createFromParcel(Parcel in) {
//            return new Media(in);
//        }
//
//        @Override
//        public Media[] newArray(int size) {
//            return new Media[size];
//        }
//    };
    private Context context;
    private int id;
    private MediaMetadataRetriever metadataRetriever;
    private MediaPlayer mediaPlayer;
    private Bitmap image;
    private String title;
    private String artist;
    private String album;
    private String duration;

    public Media(Context context, int id) {
        this.context = context;
        this.id = id;
        mediaPlayer = MediaPlayer.create(context, id);
        AssetFileDescriptor afd = context.getResources().openRawResourceFd(id);
        metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
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
            image = BitmapFactory.decodeByteArray(art, 0, art.length);
            return image;
        } catch (Exception e) {
            return null;
        }
    }

    public String getMediaTitle() {
        title = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        if (title != null)
            return title;
        return context.getString(R.string.unknown_title);
    }

    public String getMediaArtist() {
        artist = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        if (artist != null)
            return artist;
        return context.getString(R.string.unknown_artist);
    }

    public String getMediaAlbum() {
        album = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        if (album != null)
            return album;
        return context.getString(R.string.unknown_album);
    }

    public String getMediaDuration() {
        try {
            int time = mediaPlayer.getDuration();
            duration = (context.getString(R.string.duration,
                    TimeUnit.MILLISECONDS.toMinutes(time),
                    (TimeUnit.MILLISECONDS.toSeconds(time) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)))));
            return duration;
        } catch (Exception e) {
            return context.getString(R.string.duration, 0, 0);
        }
    }

}

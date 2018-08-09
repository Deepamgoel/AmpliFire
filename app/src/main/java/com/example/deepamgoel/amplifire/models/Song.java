package com.example.deepamgoel.amplifire.models;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaMetadataRetriever;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.deepamgoel.amplifire.R;

import java.util.concurrent.TimeUnit;

public class Song implements Parcelable {

    public static final Parcelable.Creator<Song> CREATOR
            = new Parcelable.Creator<Song>() {
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
    private Context context;
    private MediaMetadataRetriever metadataRetriever;
    private int id;
    private byte[] art;
    private String title;
    private String artist;
    private String album;
    private boolean like;
    private String duration;

    public Song(Context context, int id) {
        this.context = context;
        this.id = id;
        metadataRetriever = new MediaMetadataRetriever();
        AssetFileDescriptor afd = context.getResources().openRawResourceFd(id);
        metadataRetriever.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        assigner();
    }

    private Song(Parcel in) {
        id = in.readInt();
        art = in.createByteArray();
        title = in.readString();
        artist = in.readString();
        album = in.readString();
        like = in.readByte() != 0;
        duration = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByteArray(art);
        dest.writeString(getTitle());
        dest.writeString(getArtist());
        dest.writeString(getAlbum());
        dest.writeByte((byte) (like ? 1 : 0));
        dest.writeString(getDuration());
    }

    public int getId() {
        return id;
    }

    public byte[] getArt() {
        return art;
    }

    public String getTitle() {
        if (title != null)
            return title;
        return context.getString(R.string.unknown_title);
    }

    public String getArtist() {
        if (artist != null)
            return artist;
        return context.getString(R.string.unknown_artist);
    }

    public String getAlbum() {
        if (album != null)
            return album;
        return context.getString(R.string.unknown_album);
    }

    public boolean getLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public String getDuration() {
        return duration;
    }

    private void assigner() {
        art = metadataRetriever.getEmbeddedPicture();
        title = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        artist = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        album = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        setLike(false);

        try {
            Long time = Long.parseLong(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            duration = context.getString(R.string.duration,
                    TimeUnit.MILLISECONDS.toMinutes(time),
                    (TimeUnit.MILLISECONDS.toSeconds(time) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)))
            );
        } catch (Exception e) {
            duration = context.getString(R.string.duration, 0, 0);
        }
    }
}

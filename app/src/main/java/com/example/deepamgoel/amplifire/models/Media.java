package com.example.deepamgoel.amplifire.models;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaMetadataRetriever;
import android.os.Parcel;
import android.os.Parcelable;

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
    private MediaMetadataRetriever metadataRetriever;
    private int id;
    private byte[] art;
    private String title;
    private String artist;
    private String album;
    private boolean like;
    private String duration;

    public Media(Context context, int id) {
        this.context = context;
        this.id = id;
        metadataRetriever = new MediaMetadataRetriever();
        AssetFileDescriptor afd = context.getResources().openRawResourceFd(id);
        metadataRetriever.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        assigner();
    }

    private Media(Parcel in) {
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
        dest.writeString(getMediaTitle());
        dest.writeString(getMediaArtist());
        dest.writeString(getMediaAlbum());
        dest.writeByte((byte) (like ? 1 : 0));
        dest.writeString(getMediaDuration());
    }

    public int getId() {
        return id;
    }

    public byte[] getMediaArt() {
        return art;
    }

    public String getMediaTitle() {
        if (title != null)
            return title;
        return context.getString(R.string.unknown_title);
    }

    public String getMediaArtist() {
        if (artist != null)
            return artist;
        return context.getString(R.string.unknown_artist);
    }

    public String getMediaAlbum() {
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

    public String getMediaDuration() {
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

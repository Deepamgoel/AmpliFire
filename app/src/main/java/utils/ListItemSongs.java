package utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;

public class ListItemSongs {

    private MediaPlayer mediaPlayer;
    private MediaMetadataRetriever retriever;

    public ListItemSongs(Context context, int id) {
        AssetFileDescriptor afd = context.getResources().openRawResourceFd(id);
        retriever = new MediaMetadataRetriever();
        retriever.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        mediaPlayer = MediaPlayer.create(context, id);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public MediaMetadataRetriever getMetadataRetriever() {
        return retriever;
    }
}

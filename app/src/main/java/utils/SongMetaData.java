package utils;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

public class SongMetaData {
    private MediaMetadataRetriever mmr = new MediaMetadataRetriever();

    void Song(Context context, Uri uri) {
        mmr.setDataSource(context, uri);
    }

    String geTitle() {
        return mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
    }

    String getArtist() {
        return mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
    }

    byte[] getArt() {
        return mmr.getEmbeddedPicture();
    }
}

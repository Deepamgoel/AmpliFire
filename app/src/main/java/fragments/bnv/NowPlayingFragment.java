package fragments.bnv;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.deepamgoel.amplifire.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NowPlayingFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {


    // Song attributes
    @BindView(R.id.background)
    ImageView background;
    @BindView(R.id.album_art)
    ImageView albumArt;
    @BindView(R.id.album_title)
    TextView title;
    @BindView(R.id.artist)
    TextView artist;

    @BindView(R.id.played_duration)
    TextView playedDuration;
    @BindView(R.id.total_duration)
    TextView totalDuration;
    @BindView(R.id.seekbar)
    SeekBar seekBar;

    //Buttons
    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.song_info)
    ImageButton song_info;
    @BindView(R.id.repeat)
    ImageButton repeat;
    @BindView(R.id.previous)
    ImageButton previous;
    @BindView(R.id.play)
    ImageButton play;
    @BindView(R.id.next)
    ImageButton next;
    @BindView(R.id.like)
    ImageButton like;

    private MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
    private MediaPlayer mediaPlayer;

    private Handler handler = new Handler();
    private Runnable UpdateSongTime = new Runnable() {

        public void run() {
            int time = mediaPlayer.getCurrentPosition();
            playedDuration.setText(getString(R.string.duration,
                    TimeUnit.MILLISECONDS.toMinutes(time),
                    (TimeUnit.MILLISECONDS.toSeconds(time) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)))));
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            handler.postDelayed(this, 100);
        }

    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int id = getResources().getIdentifier(getResources().getResourceName(
                R.raw.heartless),
                "raw",
                getActivity().getPackageName());

        mediaPlayer = MediaPlayer.create(getActivity(), id);
        AssetFileDescriptor afd = getResources().openRawResourceFd(id);
        metadataRetriever.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());

        try {
            byte[] art = metadataRetriever.getEmbeddedPicture();
            Bitmap image = BitmapFactory.decodeByteArray(art, 0, art.length);
            background.setImageBitmap(image);
            albumArt.setImageBitmap(image);
        } catch (Exception e) {
            background.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background));
            albumArt.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background));
        }

        try {
//                Todo: Reconsider following logic
//            title.setText(getResources().getResourceName(R.raw.party_hard_chris_brown));
            title.setText(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        } catch (Exception e) {
            title.setText(R.string.unknown_album);
        }

        try {
            artist.setText(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        } catch (Exception e) {
            artist.setText(R.string.unknown_artist);
        }

        try {
            int time = mediaPlayer.getDuration();
            totalDuration.setText(getString(R.string.duration,
                    TimeUnit.MILLISECONDS.toMinutes(time),
                    (TimeUnit.MILLISECONDS.toSeconds(time) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)))));
        } catch (Exception e) {
            totalDuration.setText(getString(R.string.duration, 0, 0));
        }

        try {
            int time = mediaPlayer.getCurrentPosition();
            playedDuration.setText(getString(R.string.duration,
                    TimeUnit.MILLISECONDS.toMinutes(time),
                    (TimeUnit.MILLISECONDS.toSeconds(time) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)))));
        } catch (Exception e) {
            totalDuration.setText(getString(R.string.duration, 0, 0));
        }

        play.setOnClickListener(this);
        next.setOnClickListener(this);
        repeat.setOnClickListener(this);
        like.setOnClickListener(this);
        previous.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.play:
                if (play.getTag().equals("pause")) {
                    play.setTag("play");
                    play.setImageDrawable(getResources().getDrawable(R.drawable.pause_circle_outline));

                    mediaPlayer.start();
                    seekBar.setMax(mediaPlayer.getDuration());

                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(UpdateSongTime, 100);

                } else if (play.getTag().equals("play")) {
                    play.setTag("pause");
                    play.setImageDrawable(getResources().getDrawable(R.drawable.play_circle_outline));

                    mediaPlayer.pause();
                }
                break;

//                Todo: Update next button
            case R.id.next:
                break;

//                Todo: Update previous button
            case R.id.previous:
                break;

//                Todo: Update like button
            case R.id.like:
                if (like.getTag().equals("off")) {
                    like.setTag("on");
                    like.setImageDrawable(getResources().getDrawable(R.drawable.heart));
                    like.setColorFilter(getResources().getColor(R.color.Teal));
                } else if (like.getTag().equals("on")) {
                    like.setTag("off");
                    like.setImageDrawable(getResources().getDrawable(R.drawable.heart_outline));
                    like.setColorFilter(getResources().getColor(R.color.AliceBlue));
                }
                break;

//                Todo: Update repeat button
            case R.id.repeat:
                if (repeat.getTag().equals("off")) {
                    repeat.setTag("on");
                    repeat.setImageDrawable(getResources().getDrawable(R.drawable.repeat));
                    repeat.setColorFilter(getResources().getColor(R.color.Teal));
                } else if (repeat.getTag().equals("on")) {
                    repeat.setTag("one");
                    repeat.setImageDrawable(getResources().getDrawable(R.drawable.repeat_once));
                    repeat.setColorFilter(getResources().getColor(R.color.Teal));

                } else if (repeat.getTag().equals("one")) {
                    repeat.setTag("off");
                    repeat.setImageDrawable(getResources().getDrawable(R.drawable.repeat));
                    repeat.setColorFilter(getResources().getColor(R.color.AliceBlue));
                }
                break;

//                Todo: Update info button
            case R.id.song_info:
                break;

//                Todo: Update back button
            case R.id.back:
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mediaPlayer != null && fromUser) {
            mediaPlayer.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}


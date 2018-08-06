package com.example.deepamgoel.amplifire.fragments;

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
import com.example.deepamgoel.amplifire.models.Media;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NowPlayingFragment extends Fragment implements View.OnClickListener, MediaPlayer.OnCompletionListener {


    // Media attributes
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
    TextView duration;
    @BindView(R.id.seekbar)
    SeekBar seekBar;

    //Buttons
    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.more)
    ImageButton more;
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

    private MediaPlayer mediaPlayer;
    private Media media;

    private Handler handler = new Handler();
    private Runnable updateSongTime = new Runnable() {

        public void run() {
            if (mediaPlayer.isPlaying()) {
                int time = mediaPlayer.getCurrentPosition();
                playedDuration.setText(getString(R.string.duration,
                        TimeUnit.MILLISECONDS.toMinutes(time),
                        (TimeUnit.MILLISECONDS.toSeconds(time) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)))));
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 100);
            }
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        HandlerThread mHandlerThread;
//        mHandlerThread = new HandlerThread("HandlerThread");
//        mHandlerThread.start();
//        handler = new Handler(mHandlerThread.getLooper());

        play.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        repeat.setOnClickListener(this);
        like.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.play:
                if (mediaPlayer.isPlaying()) {
                    pause();
                } else {
                    play();
                }
                break;

            case R.id.next:
                next();
                break;

            case R.id.previous:
                previous();
                break;

            case R.id.like:
                if (like.getTag().equals("off")) {
                    like();
                } else if (like.getTag().equals("on")) {
                    dislike();
                }
                break;

            case R.id.repeat:
                if (repeat.getTag().equals("off")) {
                    repeatOn();
                } else if (repeat.getTag().equals("on")) {
                    repeatOnce();
                } else if (repeat.getTag().equals("one")) {
                    repeatOff();
                }
                break;

            case R.id.more:
                break;

            case R.id.back:
                break;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (repeat.getTag().equals("one")) {
            mediaPlayer.seekTo(0);
            play();
        } else {
            play.setTag("pause");
            play.setImageDrawable(getResources().getDrawable(R.drawable.play_circle_outline));
        }
    }

    private void play() {
        play.setTag("play");
        play.setImageDrawable(getResources().getDrawable(R.drawable.pause_circle_outline));

        mediaPlayer.start();
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        handler.postDelayed(updateSongTime, 100);
        mediaPlayer.setOnCompletionListener(this);
    }

    private void pause() {
        play.setTag("pause");
        play.setImageDrawable(getResources().getDrawable(R.drawable.play_circle_outline));
        mediaPlayer.pause();
    }

    private void next() {

    }

    private void previous() {

    }

    private void like() {
        like.setTag("on");
        like.setImageDrawable(getResources().getDrawable(R.drawable.heart));
        like.setColorFilter(getResources().getColor(R.color.Teal));
    }

    private void dislike() {
        like.setTag("off");
        like.setImageDrawable(getResources().getDrawable(R.drawable.heart_outline));
        like.setColorFilter(getResources().getColor(R.color.AliceBlue));
    }

    private void repeatOn() {
        repeat.setTag("on");
        repeat.setImageDrawable(getResources().getDrawable(R.drawable.repeat));
        repeat.setColorFilter(getResources().getColor(R.color.Teal));
    }

    private void repeatOff() {
        repeat.setTag("off");
        repeat.setImageDrawable(getResources().getDrawable(R.drawable.repeat));
        repeat.setColorFilter(getResources().getColor(R.color.AliceBlue));
    }

    private void repeatOnce() {
        repeat.setTag("one");
        repeat.setImageDrawable(getResources().getDrawable(R.drawable.repeat_once));
        repeat.setColorFilter(getResources().getColor(R.color.Teal));
    }

    public void changeData(int id) {
        media = new Media(getContext(), id);
        setData(media);
        play();
    }

    private void setData(@NonNull Media media) {

        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = media.getMediaPlayer();

        if (media.getMediaBitmap() != null) {
            albumArt.setImageBitmap(media.getMediaBitmap());
            background.setImageBitmap(media.getMediaBitmap());
        } else {
            albumArt.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background));
            background.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background));
        }
        title.setText(media.getMediaTitle());
        artist.setText(media.getMediaArtist());
        duration.setText(media.getMediaDuration());
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setProgress(0);

        try {
            int time = mediaPlayer.getCurrentPosition();
            playedDuration.setText(getString(R.string.duration,
                    TimeUnit.MILLISECONDS.toMinutes(time),
                    (TimeUnit.MILLISECONDS.toSeconds(time) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)))));
        } catch (Exception e) {
            playedDuration.setText(getString(R.string.duration, 0, 0));
        }
    }
}


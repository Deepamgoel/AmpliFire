package com.example.deepamgoel.amplifire.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.deepamgoel.amplifire.models.Song;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NowPlayingFragment extends Fragment implements
        View.OnClickListener,
        MediaPlayer.OnCompletionListener {

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
    private Song song;

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

//    public static NowPlayingFragment newInstance() {
//        NowPlayingFragment myFragment = new NowPlayingFragment();
//        Bundle args = new Bundle();
//        args.putInt("someInt", someInt);
//        myFragment.setArguments(args);
//        return myFragment;
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {

            song = savedInstanceState.getParcelable("song");

            if (song != null) {
                if (song.getLike()) {
                    dislike();
                } else if (!song.getLike()) {
                    like();
                }
                setData(song);
            }

            repeat.setTag(savedInstanceState.getString("repeat"));
            if (repeat.getTag().equals("off")) {
                repeatOn();
            } else if (repeat.getTag().equals("on")) {
                repeatOnce();
            } else if (repeat.getTag().equals("one")) {
                repeatOff();
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                if (like.getTag().equals("dislike")) {
                    like();
                } else if (like.getTag().equals("like")) {
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

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString("repeat", repeat.getTag().toString());
//        outState.putParcelable("song", song);
//    }

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
        like.setTag("like");
        like.setImageDrawable(getResources().getDrawable(R.drawable.heart));
        like.setColorFilter(getResources().getColor(R.color.Teal));
        song.setLike(true);
    }

    private void dislike() {
        like.setTag("dislike");
        like.setImageDrawable(getResources().getDrawable(R.drawable.heart_outline));
        like.setColorFilter(getResources().getColor(R.color.AliceBlue));
        song.setLike(false);
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

    public void changeData(Song song) {
        this.song = song;
        setData(song);
    }

    private void setData(@NonNull Song song) {

        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = MediaPlayer.create(getContext(), song.getId());

        if (song.getArt() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(
                    song.getArt(), 0, song.getArt().length);
            albumArt.setImageBitmap(bitmap);
            background.setImageBitmap(bitmap);
        } else {
            albumArt.setImageDrawable(getResources().getDrawable(R.drawable.default_art));
            background.setImageDrawable(getResources().getDrawable(R.drawable.default_art));
        }

        title.setText(song.getTitle());
        artist.setText(song.getArtist());
        duration.setText(song.getDuration());
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setProgress(0);
        if (song.getLike())
            like();
        else if (!song.getLike())
            dislike();

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


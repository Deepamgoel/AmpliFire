package com.example.deepamgoel.amplifire.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.deepamgoel.amplifire.R;
import com.example.deepamgoel.amplifire.models.Song;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NowPlayingActivity extends AppCompatActivity implements
        View.OnClickListener,
        MediaPlayer.OnCompletionListener {

    // Song attributes
    @BindView(R.id.backdrop)
    ImageView backdrop;
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

    private Song song;
    private PopupMenu popupMenu;
    private MediaPlayer mediaPlayer;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.no_change);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent.hasExtra("song"))
            song = intent.getParcelableExtra("song");
        else
            song = new Song(getApplicationContext(), R.raw.starboy);

        Context wrapper = new ContextThemeWrapper(this, R.style.PopUpTheme);
        popupMenu = new PopupMenu(wrapper, more);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.now_playing_menu, popupMenu.getMenu());

        play.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        repeat.setOnClickListener(this);
        like.setOnClickListener(this);
        back.setOnClickListener(this);
        more.setOnClickListener(this);
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

        if (song != null)
            setData(song);

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
                    Snackbar snackbar = Snackbar.make(v,
                            song.getTitle() + " added to favorite", Snackbar.LENGTH_SHORT);
                    snackbar.setAction("Action", null).show();
                    like();
                } else if (like.getTag().equals("like")) {
                    Snackbar snackbar = Snackbar.make(v,
                            song.getTitle() + " removed from favorite", Snackbar.LENGTH_SHORT);
                    snackbar.setAction("Action", null).show();
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
                popupMenu.show();
                break;

            case R.id.back:
                onBackPressed();
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

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(updateSongTime);
        if (isFinishing())
            overridePendingTransition(R.anim.no_change, R.anim.slide_out);
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

    private void setData(@NonNull Song song) {

        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = MediaPlayer.create(getApplicationContext(), song.getId());

        if (song.getArt() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(
                    song.getArt(), 0, song.getArt().length);
            albumArt.setImageBitmap(bitmap);
            backdrop.setImageBitmap(bitmap);
        } else {
            albumArt.setImageDrawable(getResources().getDrawable(R.drawable.default_art));
            backdrop.setImageDrawable(getResources().getDrawable(R.drawable.default_art));
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
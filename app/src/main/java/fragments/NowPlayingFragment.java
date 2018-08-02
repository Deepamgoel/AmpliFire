package fragments;

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

public class NowPlayingFragment extends Fragment implements View.OnClickListener {


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

    private MediaMetadataRetriever metadataRetriever;
    private MediaPlayer mediaPlayer;
    private AssetFileDescriptor afd;
    private View view;

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
        view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.animals);
        metadataRetriever = new MediaMetadataRetriever();

        afd = view.getResources().openRawResourceFd(R.raw.animals);
        metadataRetriever.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());

        setData();
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
//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                pause();
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.play:
                if (play.getTag().equals("pause")) {
                    play();
                } else if (play.getTag().equals("play")) {
                    pause();
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

//                Todo: Update info button
            case R.id.song_info:
                break;

//                Todo: Update back button
            case R.id.back:
                break;
        }
    }

    private void setData() {

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
            String string = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            if (string != null)
                title.setText(string);
            else title.setText(R.string.unknown_title);
        } catch (Exception e) {
            title.setText(R.string.unknown_title);
        }

        try {
            String string = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            if (string != null)
                artist.setText(string);
            else artist.setText(R.string.unknown_artist);
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
    }

    private void play() {
        play.setTag("play");
        play.setImageDrawable(getResources().getDrawable(R.drawable.pause_circle_outline));

        mediaPlayer.start();
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        handler.postDelayed(UpdateSongTime, 100);
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
        mediaPlayer = MediaPlayer.create(getContext(), id);
        afd = view.getResources().openRawResourceFd(id);
        metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        setData();
    }
}


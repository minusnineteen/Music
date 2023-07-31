package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button prev, play, stop, next;
    TextView title, start, end;
    SeekBar sb;
    ImageView img;

    ArrayList<Song> a;
    MediaPlayer mp;
    Animation anim;
    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prev = (Button) findViewById(R.id.btnPrev);
        play = (Button) findViewById(R.id.btnPlay);
        stop = (Button) findViewById(R.id.btnStop);
        next = (Button) findViewById(R.id.btnNext);
        title = (TextView) findViewById(R.id.tvSong);
        start = (TextView) findViewById(R.id.tvStart);
        end = (TextView) findViewById(R.id.tvEnd);
        sb = (SeekBar) findViewById(R.id.seekBar);
        img = (ImageView) findViewById(R.id.imageView);

        addSong();

        anim = AnimationUtils.loadAnimation(this, R.anim.rolate);

        mp = MediaPlayer.create(MainActivity.this, a.get(pos).getFile());
        title.setText(a.get(pos).getTitle());

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp.isPlaying()) {
                    mp.pause();
                    //play.setImageResource(R.drawable.);
                } else {
                    mp.start();
                    //play.setImageResource(R.drawable.);
                }
                setTimeEnd();
                setTimeStart();
                img.startAnimation(anim);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos--;
                if (pos < 0) {
                    pos = a.size() - 1;
                }
                if (mp.isPlaying()) {
                    mp.stop();
                }
                mp = MediaPlayer.create(MainActivity.this, a.get(pos).getFile());
                title.setText(a.get(pos).getTitle());
                mp.start();
                setTimeEnd();
                setTimeStart();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos++;
                if (pos > a.size() - 1) {
                    pos = 0;
                }
                if (mp.isPlaying()) {
                    mp.stop();
                }
                mp = MediaPlayer.create(MainActivity.this, a.get(pos).getFile());
                title.setText(a.get(pos).getTitle());
                mp.start();
                setTimeEnd();
                setTimeStart();
            }
        });

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(sb.getProgress());
            }
        });
    }

    private void addSong() {
        a = new ArrayList<>();
        a.add (new Song("Happy Birthday", R.raw.hbty));
        a.add (new Song("Happy New Year", R.raw.hny));
    }

    private void setTimeEnd() {
        SimpleDateFormat fm = new SimpleDateFormat("mm:ss");
        end.setText(fm.format(mp.getDuration()));
        sb.setMax(mp.getDuration());
    }

    private void setTimeStart() {
        Handler hl = new Handler();
        hl.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat fm = new SimpleDateFormat("mm:ss");
                start.setText(fm.format(mp.getCurrentPosition()));
                sb.setProgress(mp.getCurrentPosition());

                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        pos++;
                        if (pos > a.size() - 1) {
                            pos = 0;
                        }
                        if (mp.isPlaying()) {
                            mp.stop();
                        }
                        mp = MediaPlayer.create(MainActivity.this, a.get(pos).getFile());
                        title.setText(a.get(pos).getTitle());
                        mp.start();
                        setTimeEnd();
                        setTimeStart();
                    }
                });

                hl.postDelayed(this, 500);
            }
        }, 100);
    }

}

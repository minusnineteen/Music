package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button prev, play, stop, next;
    TextView title, start, end;
    SeekBar sb;

    ArrayList<Song> a;

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

        addSong();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mp = MediaPlayer.create(MainActivity.this, a.get(pos).getFile());
                mp.start();
                title.setText(a.get(pos).getTitle());
            }
        });

    }

    private void addSong() {
        a = new ArrayList<>();
        a.add (new Song("HBTY", R.raw.happy));
    }
}
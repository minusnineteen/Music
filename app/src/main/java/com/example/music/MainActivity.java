package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lv_listSong;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_listSong = findViewById(R.id.lv_listSong);

        DatabaseHelper db = new DatabaseHelper(MainActivity.this);
//        db.deleteAllSongs();
//        this.deleteDatabase(DatabaseHelper.DATABASE_NAME);

        db.addSong("Happy","duc  HUNG 2","ballad",R.raw.happy);
        db.addSong("Sao em lại tắt máy","duc  HUNG 3","ballad",R.raw.saoemlaitatmay);
        db.addSong("bgm","duc  HUNG 1","ballad",R.raw.bgm);
        db.addSong("Lời tâm sự số 3","duc  HUNG 4","ballad",R.raw.loitamsuso3);


        List<Song> songList = db.getAllSongs();

        ArrayAdapter<Song> adapter = new ArrayAdapter<Song>(this, R.layout.list_item_song,R.id.tv_titleSong, songList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                Song song = getItem(position);
                TextView textViewSongTitle = view.findViewById(R.id.tv_titleSong);
                textViewSongTitle.setText(song.getTitle());
                return view;
            }
        };
        lv_listSong.setAdapter(adapter);

        lv_listSong.setOnItemClickListener(new  AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy bài hát tại vị trí đã chọn
                Song selectedSong = songList.get(position);

                // Phát nhạc của bài hát đã chọn
                playMusic(selectedSong);

                Intent intent = new Intent(MainActivity.this, PlayMusicActivity.class);
                startActivity(intent);
            }
        });

    }
    private void playMusic(Song song) {
        // Kiểm tra nếu đang phát nhạc khác, thì dừng nhạc đó trước khi phát nhạc mới
        if(isPlaying) {
            stopMusic();
        }

        isPlaying = true;

        // Khởi tạo Media Player với đường dẫn của bài hát
        mediaPlayer = MediaPlayer.create(this, song.getPath());
//        try {
//            mediaPlayer = MediaPlayer.create(this, R.raw.bgm);
//            //mediaPlayer.setDataSource(this,     R.raw.bgm);
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        mediaPlayer.start();
    }

    private void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        isPlaying = false;
    }

}
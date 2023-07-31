package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
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

    Button btn_DangXuat;
    TextView tv_hello;
    ListView lv_listGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_listSong = findViewById(R.id.lv_listSong);
        lv_listGenre = findViewById(R.id.lv_listGenre);
        btn_DangXuat = findViewById(R.id.btn_DangXuat);
        tv_hello = findViewById(R.id.tv_hello);

        DatabaseHelper db = new DatabaseHelper(MainActivity.this);
     //db.deleteAllSongs();
       //this.deleteDatabase(DatabaseHelper.DATABASE_NAME);

        db.addGenre("Pop");
        db.addGenre("Ballad");

        db.addSong("Happy","duc  HUNG 2","Pop",R.raw.happy);
        db.addSong("Sao em lại tắt máy","duc  HUNG 3","Ballad",R.raw.saoemlaitatmay);
        db.addSong("bgm","duc  HUNG 1","Pop",R.raw.bgm);
        db.addSong("Lời tâm sự số 3","duc  HUNG 4","Meo",R.raw.loitamsuso3);
        db.addSong("Lời tâm sự số 4","duc  HUNG 4","House",R.raw.loitamsuso3);


        List<Song> songList = db.getAllSongs();
        List<Genre> genreList = db.getAllGenres();

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

        ArrayAdapter<Genre> adapterGenre = new ArrayAdapter<Genre>(this, R.layout.list_item_genre,R.id.tv_titleGenre, genreList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                Genre genre = getItem(position);
                TextView textViewGenreTitle = view.findViewById(R.id.tv_titleGenre);
                textViewGenreTitle.setText(genre.getName());
                return view;
            }
        };
        lv_listGenre.setAdapter(adapterGenre);

        lv_listSong.setOnItemClickListener(new  AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy bài hát tại vị trí đã chọn
                Song selectedSong = songList.get(position);

                // Phát nhạc của bài hát đã chọn
                playMusic(selectedSong);

                Intent intent = new Intent(MainActivity.this, PlayMusicActivity.class);

//                intent.putExtra("song", (Parcelable) selectedSong);

                startActivity(intent);
            }
        });


        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        String username = preferences.getString("username", "");
        tv_hello.setText("XIN CHÀO " + username);

        btn_DangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
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

    private void logOut() {
        // Xóa thông tin đăng nhập trong SharedPreferences khi người dùng đăng xuất
        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMusic();
    }
}
package com.example.music;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "music.db";
    private static final int DATABASE_VERSION = 1;
    // Bảng user
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    // Bảng song
    private static final String TABLE_SONGS = "songs";
    private static final String COLUMN_SONG_ID = "id";
    private static final String COLUMN_SONG_TITLE = "title";
    private static final String COLUMN_SONG_ARTIST = "artist";
    private static final String COLUMN_SONG_GENRE = "genre";
    private static final String COLUMN_SONG_PATH = "path";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createTableQuery);

        String createSongsTableQuery = "CREATE TABLE " + TABLE_SONGS + "("
                + COLUMN_SONG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SONG_TITLE + " TEXT,"
                + COLUMN_SONG_ARTIST + " TEXT,"
                + COLUMN_SONG_GENRE + " TEXT,"
                + COLUMN_SONG_PATH + " INTEGER)";
        db.execSQL(createSongsTableQuery);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQueryUsers = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQueryUsers);

        String dropTableQuerySongs = "DROP TABLE IF EXISTS " + TABLE_SONGS;
        db.execSQL(dropTableQuerySongs);

        onCreate(db);
    }

    public long addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        long newRowId = db.insert(TABLE_NAME, null, values);
        db.close();

        return newRowId;
    }

    public boolean isUserValid(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        boolean isValid = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return isValid;
    }

    public long addSong(String title, String artist, String genre, int path) {

        if (isSongExist(title, artist)) {
            return -1;
        }
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SONG_TITLE, title);
        values.put(COLUMN_SONG_ARTIST, artist);
        values.put(COLUMN_SONG_GENRE, genre);
        values.put(COLUMN_SONG_PATH, path);

        long newRowId = db.insert(TABLE_SONGS, null, values);
        db.close();

        return newRowId;
    }

    public List<Song> getAllSongs() {
        List<Song> songList = new ArrayList<Song>();
        String SELECT_QUERY = "SELECT * FROM " + TABLE_SONGS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(SELECT_QUERY, null);
        if (c.moveToFirst()) {
            do {
                Song s = new Song();
                s.setId(c.getInt(0));
                s.setTitle(c.getString(1));
                s.setArtist(c.getString(2));
                s.setGenre(c.getString(3));
                s.setPath(c.getInt(4));

                songList.add(s);
            } while (c.moveToNext());
        }
        return songList;
    }


    public boolean isSongExist(String title, String artist) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_SONG_TITLE + " = ? AND " + COLUMN_SONG_ARTIST + " = ?";
        String[] selectionArgs = {title, artist};

        Cursor cursor = db.query(TABLE_SONGS, null, selection, selectionArgs, null, null, null);

        boolean isExist = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return isExist;
    }


    public void deleteAllSongs() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SONGS, null, null);
        db.close();
    }

}

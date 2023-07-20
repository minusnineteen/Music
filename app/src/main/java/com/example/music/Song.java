package com.example.music;

public class Song {
    private String title;
    private int file;

    public Song(String t, int f) {
        title = t;
        file = f;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }
}

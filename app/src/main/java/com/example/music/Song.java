package com.example.music;

public class Song {
    private int id;
    private String title;
    private String artist;
    private String genre;
    private int path;

    public Song() {}

    public Song(int id, String title, String artist, String genre ,int path) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.path = path;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }
    @Override
    public String toString() {
        return title;
    }
}

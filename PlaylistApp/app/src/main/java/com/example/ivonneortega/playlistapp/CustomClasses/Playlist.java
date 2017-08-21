package com.example.ivonneortega.playlistapp.CustomClasses;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to represent a playlist.
 */

public class Playlist {

    private String mName;
    private List<Song> mSongList;

    public Playlist(String name) {
        mName = name;
        mSongList = new ArrayList<>();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Song> getSongList() {
        return mSongList;
    }

    public void setSongList(List<Song> songList) {
        mSongList = songList;
    }
}

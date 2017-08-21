package com.example.ivonneortega.playlistapp.CustomClasses;

import com.example.ivonneortega.playlistapp.GSON_Classes.Artist_;

import java.util.List;

/**
 * Class used to represent a song.
 */

public class Song {

    private String mName;
    private String mArtistNames;

    public Song(String name, List<Artist_> artistNames) {
        mName = name;
        String aux="";
        for (Artist_ artist: artistNames) {
            if(aux.trim().isEmpty())
                aux=artist.getName();
            else
                aux = aux+", "+artist.getName();
        }
        mArtistNames = aux;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getArtistNames() {
        return mArtistNames;
    }

    public void setArtistNames(String artistNames) {
        mArtistNames = artistNames;
    }
}


package com.example.ivonneortega.playlistapp.GSON_Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Songs {

    @SerializedName("tracks")
    @Expose
    private Tracks tracks;

    public Tracks getTracks() {
        return tracks;
    }

    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

}

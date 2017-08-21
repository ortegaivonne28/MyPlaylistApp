package com.example.ivonneortega.playlistapp.Swipe;

/**
 * Created by ivonneortega on 8/20/17.
 */

public interface SwipeInterface {

    public static final int PLAYLIST = 0;
    public static final int SONG = 1;

    void onItemDismiss(int position,int type);
}

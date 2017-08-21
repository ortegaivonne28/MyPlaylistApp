package com.example.ivonneortega.playlistapp;

import com.example.ivonneortega.playlistapp.CustomClasses.Playlist;
import com.example.ivonneortega.playlistapp.CustomClasses.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivonneortega on 8/20/17.
 */


/**
 * Singleton used to keep data between activities and fragments
 * Initially a list of playlists and each playlist has a list of songs
 */
public class Singleton {

    public static Singleton sListSinglenton;
    List<Playlist> mPlaylists;


    String access_token;


    public static Singleton getInstance() {
        if (sListSinglenton == null) {
            sListSinglenton = new Singleton();
        }
        return sListSinglenton;
    }

    public String getAccess_token() {
        return access_token;
    }

    public List<Song> getSongs(int position)
    {
        return mPlaylists.get(position).getSongList();
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Singleton() {
        mPlaylists = new ArrayList<>();
    }

    public void addList(Playlist listNames) {
        mPlaylists.add(listNames);
    }

    public List<Playlist> getPlaylists() {
        return mPlaylists;
    }

    public void removeList(int position) {
        if (position >= mPlaylists.size() && position <= mPlaylists.size())
            mPlaylists.remove(position);
    }

    public void setListNames(List<Playlist> listNames) {
        mPlaylists = listNames;
    }

    public void deleteAll() {
        for (int i = mPlaylists.size() - 1; i >= 0; i--) {
            mPlaylists.remove(i);
        }
    }

    public void addSongToPlaylist(int position, Song song)
    {
        mPlaylists.get(position).getSongList().add(song);
    }

    public void removePlaylist(int position)
    {
        mPlaylists.remove(position);
    }

    public void removeSongFromPlaylist(int playListPosition,int songPosition)
    {
        mPlaylists.get(playListPosition).getSongList().remove(songPosition);
    }

    public Song getSong(int playlistPosition, int songPosition)
    {
        return mPlaylists.get(playlistPosition).getSongList().get(songPosition);
    }

    public Playlist getPlaylist(int playlistPosition)
    {
        return mPlaylists.get(playlistPosition);
    }

    public void addSongInSelectedPosition (int plyalistPosition, Song song, int songPosition)
    {
        mPlaylists.get(plyalistPosition).getSongList().add(songPosition,song);
    }

    public void addPlaylistInSelectedPosition (int plyalistPosition, Playlist playlist)
    {
        mPlaylists.add(plyalistPosition,playlist);
    }
}

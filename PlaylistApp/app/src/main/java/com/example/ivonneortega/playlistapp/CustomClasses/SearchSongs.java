package com.example.ivonneortega.playlistapp.CustomClasses;

import com.example.ivonneortega.playlistapp.GSON_Classes.Item;
import com.example.ivonneortega.playlistapp.GSON_Classes.Songs;
import com.example.ivonneortega.playlistapp.GSON_Classes.Tracks;
import com.example.ivonneortega.playlistapp.Singleton;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Class used for each API call
 */

public class SearchSongs {

    private List<String> songs;
    private String aux;


    public List<Song> search (String songName)
    {
        aux = searchSong(songName);

        Gson gson = new Gson();
        Songs myTypes = gson.fromJson(aux,Songs.class);

        Tracks track = myTypes.getTracks();
        List<Item> items = new ArrayList<>();
        items = track.getItems();
        songs = new ArrayList<String>();
        List<Song> songList = new ArrayList<>();
        for (Item item: items) {

            songs.add(item.getName());

            Song aux_song = new Song(item.getName(),item.getArtists());
            songList.add(aux_song);

        }
        return songList;
    }

    public String searchSong(final String songName)
    {

        String access_token = Singleton.getInstance().getAccess_token();
        if(songName!=null && !songName.trim().isEmpty())
        {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("q",songName)
                    .add("type","track")
                    .add("limit","10")
                    .build();


            String authorizationBearer = "Bearer "+access_token;
            String url = "https://api.spotify.com/v1/search?q="+songName+"&type=track&limit=3";


            final Request request = new Request.Builder()
                    .url(url)
                    .header("Authorization",authorizationBearer)
                    .build();

            Response response;
            OkHttpClient topClient = new OkHttpClient();
            try {
                response = topClient.newCall(request).execute();

                aux = response.body().string();
                return aux;


            } catch (IOException e) {
                e.printStackTrace();
            }



        }
        return null;
    }
}

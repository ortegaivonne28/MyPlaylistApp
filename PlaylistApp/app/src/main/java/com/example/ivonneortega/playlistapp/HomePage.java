package com.example.ivonneortega.playlistapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ivonneortega.playlistapp.CustomClasses.Playlist;
import com.example.ivonneortega.playlistapp.GSON_Classes.Item;
import com.example.ivonneortega.playlistapp.GSON_Classes.Songs;
import com.example.ivonneortega.playlistapp.GSON_Classes.Tracks;
import com.example.ivonneortega.playlistapp.RecyclerViewAdapter.PlaylistRecyclerView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomePage extends AppCompatActivity {

    String access_token = null;
    private String songName;
    private RecyclerView mRecyclerView;
    private PlaylistRecyclerView mAdapter;
    private List<Playlist> mPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Playlist App");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               createNewPlaylist();
            }
        });



        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewHopePage);


    }

    @Override
    protected void onResume() {
        super.onResume();

        mPlaylist = Singleton.getInstance().getPlaylists();

        LinearLayoutManager linear = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linear);

        mAdapter = new PlaylistRecyclerView(mPlaylist,null,-1);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        authSpotify();
    }

    /**
     * Method used to create a playlist by creating an alert dialog
     */
    public void createNewPlaylist()
    {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.create_playlist, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setView(promptsView);


        final EditText playlistName = (EditText) promptsView.findViewById(R.id.playlistName);
        Button create = (Button) promptsView.findViewById(R.id.create);
        Button cancel = (Button) promptsView.findViewById(R.id.cancel);


        final AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

        if(alertDialog.isShowing())
        {
            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = playlistName.getText().toString();
                    Playlist newPlaylist = new Playlist(name);
                    Singleton.getInstance().addList(newPlaylist);
                    mAdapter.addPlaylist();

                    alertDialog.cancel();
                    Toast.makeText(HomePage.this, "Playlist created", Toast.LENGTH_SHORT).show();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();
                }
            });
        }
    }


    /**
     * Method used to authenticate the app with Spotify
     */
    public void authSpotify()
    {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("grant_type","client_credentials")
                .build();


        final Request request = new Request.Builder()
                .url("https://accounts.spotify.com/api/token")
                .post(body)
                .header("Authorization","Basic NTg1NmIxZjQ3ZGJhNDE0ZmJkYTg2NTdlN2FkOWU5NTQ6YWNjNWMyOGY0YzZkNDIxMzk4MDY4YjAwYWUwZmMwOTI=")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful())
                {
                    throw new IOException("Unexpected code: +"+ response);
                }
                else
                {
                    String responseBody = response.body().string();

                    try {
                        JSONObject results = new JSONObject(responseBody);
                        access_token = results.getString("access_token");
                        System.out.println(access_token);
                        Singleton.getInstance().setAccess_token(access_token);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

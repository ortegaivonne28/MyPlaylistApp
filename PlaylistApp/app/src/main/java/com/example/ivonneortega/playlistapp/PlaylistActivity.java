package com.example.ivonneortega.playlistapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.example.ivonneortega.playlistapp.CustomClasses.Song;
import com.example.ivonneortega.playlistapp.RecyclerViewAdapter.PlaylistRecyclerView;
import com.example.ivonneortega.playlistapp.Swipe.SimpleItemTouchHelperCallback;

import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    private String mPlaylistName;
    private AutoCompleteTextView mSearch;
    private List<String> songs;
    private AutoCompleteAdapter mAutoCompleteAdapter;
    private RecyclerView mRecyclerView;
    private PlaylistRecyclerView mRecyclerAdapter;
    private int mPosition;
    private boolean fabState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        mPlaylistName = intent.getStringExtra("name");
        mPosition = intent.getIntExtra("position",-1);

        getSupportActionBar().setTitle(mPlaylistName);
        mSearch = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        mRecyclerView = (RecyclerView) findViewById(R.id.playlistRecyclerView);



        mSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mSearch.setText("");

                List<Song> songs = mAutoCompleteAdapter.getSongs();
                Singleton.getInstance().getPlaylists().get(mPosition).getSongList().add(songs.get(position));
                mRecyclerAdapter.addSong(mPosition);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.song_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addSong) {

            //Fab state keeps the state of the icon (false means it's not activated yet
            //true means it's activated)
            if(fabState==false)
            {
                item.setIcon(R.mipmap.ic_done_white_24dp);
                mSearch.setVisibility(View.VISIBLE);
                mSearch.setText("");
                mAutoCompleteAdapter = new AutoCompleteAdapter(mSearch.getContext(), android.R.layout.simple_dropdown_item_1line);
                mSearch.setAdapter(mAutoCompleteAdapter);
                fabState=true;
            }
            else
            {
                item.setIcon(R.mipmap.ic_add_white_24dp);
                fabState=false;
                mSearch.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mSearch.getWindowToken(), 0);
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        final List<Song> songList = Singleton.getInstance().getSongs(mPosition);
        mRecyclerAdapter = new PlaylistRecyclerView(null,songList,mPosition);

        mRecyclerView.setAdapter(mRecyclerAdapter);


        //Class used for swiping in recycler view
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(mRecyclerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
    }
}

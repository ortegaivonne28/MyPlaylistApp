package com.example.ivonneortega.playlistapp;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.ivonneortega.playlistapp.CustomClasses.SearchSongs;
import com.example.ivonneortega.playlistapp.CustomClasses.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivonneortega on 8/20/17.
 */

class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    private List<Song> mSongs;


    public AutoCompleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        mSongs = new ArrayList<Song>();
    }

    @Override
    public int getCount() {
        return mSongs.size();
    }

    public List<Song> getSongs()
    {
        return mSongs;
    }

    @Override
    public String getItem(int index) {
        return mSongs.get(index).getName();
    }

    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint != null) {
                    // A class that queries a web API, parses the data and returns an ArrayList<Style>
                    SearchSongs fetcher = new SearchSongs();
                    try {
                        mSongs = fetcher.search(constraint.toString());
                    }
                    catch(Exception e) {
                        Log.e("myException", e.getMessage());
                    }
                    // Now assign the values and count to the FilterResults object
                    filterResults.values = mSongs;
                    filterResults.count = mSongs.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence contraint, FilterResults results) {
                if(results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return myFilter;
    }
}
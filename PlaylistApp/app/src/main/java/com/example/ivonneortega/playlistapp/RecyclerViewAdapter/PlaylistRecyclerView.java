package com.example.ivonneortega.playlistapp.RecyclerViewAdapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivonneortega.playlistapp.CustomClasses.Playlist;
import com.example.ivonneortega.playlistapp.PlaylistActivity;
import com.example.ivonneortega.playlistapp.R;
import com.example.ivonneortega.playlistapp.Singleton;
import com.example.ivonneortega.playlistapp.CustomClasses.Song;
import com.example.ivonneortega.playlistapp.Swipe.SwipeInterface;

import java.util.List;

/**
 * Created by ivonneortega on 8/20/17.
 */

public class PlaylistRecyclerView extends RecyclerView.Adapter implements SwipeInterface {

    private List<Playlist> mPlaylistList;
    private List<Song> mSongList;
    private int mPlaylistPosition;
    private View mView;

    public PlaylistRecyclerView(List<Playlist> playlistList, List<Song> songList, int playListPosition) {
        mPlaylistList = playlistList;
        mSongList = songList;
        mPlaylistPosition = playListPosition;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(mSongList==null)
        {
            View parentView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.playlist_recyclerview,parent,false);

            PlaylistViewHolder vh = new PlaylistViewHolder(parentView);
            mView=vh.mRoot;
            return vh;
        }
        else
        {
            View parentView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.songs_recyclerview,parent,false);

            SongsViewHolder vh = new SongsViewHolder(parentView);
            mView = vh.mArtitsNames;
            return vh;
        }


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof PlaylistViewHolder)
        {
            ((PlaylistViewHolder) holder).mTextView.setText(mPlaylistList.get(position).getName());
            ((PlaylistViewHolder) holder).mRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext().getApplicationContext(),PlaylistActivity.class);
                    intent.putExtra("name",mPlaylistList.get(holder.getAdapterPosition()).getName());
                    intent.putExtra("position",holder.getAdapterPosition());
                    v.getContext().startActivity(intent);
                }
            });

            ((PlaylistViewHolder) holder).mRoot.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mView.getContext());
                    builder.setMessage("Are you sure you want to delete this playlist")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Singleton.getInstance().removePlaylist(holder.getAdapterPosition());
                                    notifyItemRemoved(holder.getAdapterPosition());
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.create();
                    builder.show();

                    return false;
                }
            });
        }
        if(holder instanceof SongsViewHolder)
        {
            ((SongsViewHolder) holder).mSongName.setText(mSongList.get(position).getName());
            ((SongsViewHolder) holder).mArtitsNames.setText(mSongList.get(position).getArtistNames());
        }
    }

    @Override
    public int getItemCount() {
        if(mPlaylistList!=null)
            return mPlaylistList.size();
        return mSongList.size();
    }

    public void addPlaylist()
    {
        mPlaylistList = Singleton.getInstance().getPlaylists();
        notifyDataSetChanged();
    }

    public void addSong(int position)
    {
        mSongList = Singleton.getInstance().getSongs(position);
        notifyDataSetChanged();
    }

    @Override
    public void onItemDismiss(final int position, int type) {
        if(type == SwipeInterface.PLAYLIST)
        {
            final Playlist aux = Singleton.getInstance().getPlaylist(position);
            Singleton.getInstance().removePlaylist(position);
            notifyItemRemoved(position);
            Snackbar.make(mView, "Playlist deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            mSongList.add(position,aux);
                            notifyItemInserted(position);
                            Singleton.getInstance().addPlaylistInSelectedPosition(position,aux);
                        }
                    })
                    .show();
        }
        else
        {



            final Song aux = Singleton.getInstance().getSong(mPlaylistPosition,position);
            Singleton.getInstance().removeSongFromPlaylist(mPlaylistPosition,position);
            notifyItemRemoved(position);
            Snackbar.make(mView, "Song deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            notifyItemInserted(position);
                            Singleton.getInstance().addSongInSelectedPosition(mPlaylistPosition,aux,position);
                        }
                    })
                    .show();
        }
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder{

        TextView mTextView;
        View mRoot;
        public PlaylistViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.name);
            mRoot = itemView.findViewById(R.id.root);
        }
    }

    public class SongsViewHolder extends RecyclerView.ViewHolder{

        TextView mSongName,mArtitsNames;
        View mRoot;
        public SongsViewHolder(View itemView) {
            super(itemView);
            mSongName = (TextView) itemView.findViewById(R.id.song_name);
            mArtitsNames = (TextView) itemView.findViewById(R.id.articts_names);
//            mRoot = itemView.findViewById(R.id.root);
        }
    }
}



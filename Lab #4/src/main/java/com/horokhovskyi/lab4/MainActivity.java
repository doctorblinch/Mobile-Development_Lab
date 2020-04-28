package com.horokhovskyi.lab4;

import android.net.Uri;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final String[] listContent = {"Tom & Jerry", "Jazz music", "Sea shore sounds", "Concert"};
    private final Integer[] resID = { R.raw.tom, R.raw.jazz, R.raw.sea, R.raw.videoplayback};


    private VideoView videoPlayer;
    private Spinner songsNames;

    private String lastPlayed = "";

    //private Dictionary<String, Integer> songDict = new Hashtable<>();
    private Map<String, Integer> songDict = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSpinner();
        initVideoPlayer();
    }


    private void initSpinner(){

        for (int i=0; i < listContent.length; i++){
            songDict.put(listContent[i], resID[i]);
        }

        // Spinner
        songsNames = (Spinner) findViewById(R.id.songsNames);

        ArrayAdapter<String> songAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_expandable_list_item_1, listContent);

        songAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        songsNames.setAdapter(songAdapter);
    }

    private void initVideoPlayer(){

        String songName = songsNames.getSelectedItem().toString();

        videoPlayer =  (VideoView)findViewById(R.id.videoPlayer);
        Uri myVideoUri = Uri.parse( "android.resource://" + getPackageName() + "/" + songDict.get(songName));
        videoPlayer.setVideoURI(myVideoUri);
        MediaController mediaController = new MediaController(this);
        videoPlayer.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoPlayer);
    }

    public void play(View view){
        String songName = songsNames.getSelectedItem().toString();
        if (songName.equals(lastPlayed)){
            videoPlayer.start();
        }else {
            stop(view);
            initVideoPlayer();
            videoPlayer.start();
        }
        lastPlayed = songName;

    }
    public void pause(View view){
        videoPlayer.pause();
    }

    public void stop(View view){
        videoPlayer.stopPlayback();
        videoPlayer.resume();
    }
}

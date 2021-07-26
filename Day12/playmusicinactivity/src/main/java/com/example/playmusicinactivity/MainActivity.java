package com.example.playmusicinactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMediaPlayerIfNeed();
    }

    private void initMediaPlayerIfNeed() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.music);
        }
    }

    /*
        播放
     */
    public void onClickPlay(View view) {
        initMediaPlayerIfNeed();

        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    /*
        停止
     */
    public void onClickStop(View view) {
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    /*
        暂停
     */
    public void onClickPause(View view) {
        initMediaPlayerIfNeed();

        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    /*
        退出
     */
    public void onClickExit(View view) {
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
        mMediaPlayer = null;
        finish();
    }
}
package com.example.playmusicinservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MusicService extends Service {

    private class InnerBinder extends Binder implements IMusicControl {

        private MediaPlayer mMediaPlayer;

        public void initMediaPlayerIfNeed() {
            if (mMediaPlayer == null) {
                mMediaPlayer = MediaPlayer.create(MusicService.this, R.raw.music);
                mMediaPlayer.setLooping(true);
            }
        }

        @Override
        public void play() {
            initMediaPlayerIfNeed();

            if (!mMediaPlayer.isPlaying()) {
                mMediaPlayer.start();
            }
        }

        @Override
        public void pause() {
            initMediaPlayerIfNeed();

            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            }
        }

        @Override
        public void stop() {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.reset();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
        }
    }

    private InnerBinder mBinder;

    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        mBinder = new InnerBinder();
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBinder != null) {
            mBinder.stop();
        }
        stopSelf();
    }
}
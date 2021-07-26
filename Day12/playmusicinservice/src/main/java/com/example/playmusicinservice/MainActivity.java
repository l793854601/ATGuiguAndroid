package com.example.playmusicinservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private IMusicControl mMusicControl;

    private boolean mServiceBound;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof IMusicControl) {
                mMusicControl = (IMusicControl) service;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMusicControl = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startAndBindService();
    }

    private void startAndBindService() {
        if (!mServiceBound && mMusicControl == null) {
            Intent intent = new Intent(this, MusicService.class);
            startService(intent);
            mServiceBound = bindService(intent, mConnection, BIND_AUTO_CREATE);
        }
    }

    private void unbindAndStopService() {
        if (mServiceBound && mMusicControl != null) {
            unbindService(mConnection);
            Intent intent = new Intent(this, MusicService.class);
            stopService(intent);
            mServiceBound = false;
            mMusicControl = null;
        }
    }

    public void onClickPlay(View view) {
        if (mMusicControl != null) {
            mMusicControl.play();
        }
    }

    public void onClickStop(View view) {
        if (mMusicControl != null) {
            mMusicControl.stop();
        }
    }

    public void onClickPause(View view) {
        if (mMusicControl != null) {
            mMusicControl.pause();
        }
    }

    public void onClickExit(View view) {
        unbindAndStopService();
        finish();
    }
}
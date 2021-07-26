package com.example.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

public class MyRemoteService extends Service {

    private static final String TAG = "MyRemoteService";
    
    private List<Student> mStudents = Student.students();

    private class StudentBinder extends IStudentService.Stub {

        @Override
        public Student getStudent(int id) throws RemoteException {
            for (Student student : mStudents) {
                if (student.getId() == id) {
                    return student;
                }
            }
            return null;
        }
    }

    public MyRemoteService() {
        Log.d(TAG, "MyRemoteService: ");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        String action = intent.getAction();
        if (!TextUtils.isEmpty(action) && "com.example.aidlservice.IStudentService".equals(action)) {
            return new StudentBinder();
        }
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
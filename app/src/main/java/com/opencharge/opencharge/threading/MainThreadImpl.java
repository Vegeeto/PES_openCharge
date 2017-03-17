package com.opencharge.opencharge.threading;

import android.os.Handler;
import android.os.Looper;

import com.opencharge.opencharge.domain.executor.MainThread;

/**
 * Created by ferran on 15/3/17.
 *
 * This class makes sure that the runnable we provide will be run on the main UI thread.
 */
public class MainThreadImpl implements MainThread {
    private static MainThread sMainThread;

    private Handler mHandler;

    private MainThreadImpl() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }

    public static MainThread getInstance() {
        if (sMainThread == null) {
            sMainThread = new MainThreadImpl();
        }

        return sMainThread;
    }
}

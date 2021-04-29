package co.com.ceiba.serialportapp;

import android.os.FileObserver;
import android.util.Log;

import androidx.annotation.Nullable;

public class SerialPortObserver extends FileObserver {

    public SerialPortObserver(String path) {
        super(path);
    }

    @Override
    public void onEvent(int event, @Nullable String path) {

        if (event == MODIFY) {
            Log.i("File Changed", "The file was changed");
        }

    }
}

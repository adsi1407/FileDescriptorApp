/*
 * Copyright 2009 Cedric Priscal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.com.ceiba.serialportapp;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialPort {

    private Context context;
    private static final String TAG = "SerialPort";

    //private final FileInputStream mFileInputStream;
    private final OutputStream mFileOutputStream;

    public SerialPort(Context context, File portPath, int baudrate, int flags) throws SecurityException, IOException {

        this.context = context;
        /* Check access permission */
        /*if (!portPath.canRead() || !portPath.canWrite()) {
            try {
                // Missing read/write permission, trying to chmod the file
                Process su;
                su = Runtime.getRuntime().exec("/system/bin/su");
                String cmd = "chmod 666 " + portPath.getAbsolutePath() + "\n" + "exit\n";
                su.getOutputStream().write(cmd.getBytes());
                if ((su.waitFor() != 0) || !portPath.canRead() || !portPath.canWrite()) {
                    throw new SecurityException();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new SecurityException();
            }
        }*/

        /*FileDescriptor mFd = open(portPath.getAbsolutePath(), baudrate, flags);
        if (mFd == null) {
            Log.e(TAG, "native open returns null");
            throw new IOException();
        }
        mFileInputStream = new FileInputStream(mFd);
        mFileOutputStream = new FileOutputStream(mFd);*/

        Uri portUri = Uri.fromFile(portPath);

        ContentResolver contentResolver = context.getContentResolver();
        Log.i("Port Path", portPath.getAbsolutePath());
        Log.i("Uri Authority", portUri.getAuthority());
        //ParcelFileDescriptor fileDescriptor = contentResolver.openFileDescriptor(portUri, "r");

        mFileOutputStream = contentResolver.openOutputStream(portUri);


        //mFileInputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        //mFileOutputStream = new FileOutputStream(fileDescriptor.getFileDescriptor());
    }

    // Getters and setters
    /*public InputStream getInputStream() {
        return mFileInputStream;
    }*/

    public OutputStream getOutputStream() {
        return mFileOutputStream;
    }

    // JNI
    private native static FileDescriptor open(String path, int baudrate, int flags);
}


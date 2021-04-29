package co.com.ceiba.serialportapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import co.com.ceiba.serialportapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SerialPort serialPort;
    private SerialPortObserver serialPortObserver;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        serialPortObserver = new SerialPortObserver("/dev/test.txt");
        serialPortObserver.startWatching();

        binding.btnSendMessage.setOnClickListener(v -> {

            FileOutputStream nmoutfile;

            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                try {
                    String message = "Hola desde Android";
                    serialPort = new SerialPort(this, new File("/dev/test.txt"), 9600, 0);
                    serialPort.getOutputStream().write(message.getBytes());
                    serialPort.getOutputStream().flush();
                    serialPort.getOutputStream().close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        });

        binding.btnSelectImage.setOnClickListener(v -> {

            Intent requestFileIntent = new Intent(Intent.ACTION_PICK);
            requestFileIntent.setType("image/jpg");
            startActivityForResult(requestFileIntent, 0);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {

            Uri returnUri = data.getData();

            try {
                ParcelFileDescriptor inputPFD = getContentResolver().openFileDescriptor(returnUri, "r");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
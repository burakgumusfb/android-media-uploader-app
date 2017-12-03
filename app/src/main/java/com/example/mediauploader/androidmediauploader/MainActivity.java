package com.example.mediauploader.androidmediauploader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Common.Constants;
import Helper.Helper;
import HttpModules.IMedia;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> MediasPaths = new ArrayList<>();
    int IMAGE_PICKER_SELECT = 0;
    private Button btnChoose;
    private Button btnUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isStoragePermissionGranted();
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.REST_BASE_SERVICE)
                .build();
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent mediaIntent = new Intent(
                         Intent.ACTION_GET_CONTENT
                        //,Uri.parse(Environment.DIRECTORY_DCIM)
                );
               // mediaIntent.setType("*/*");
                mediaIntent.setType("image/*, video/*");
                mediaIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*"});
                mediaIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(mediaIntent, 1);
            }
        });

        btnUpload = (Button) findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IMedia media = restAdapter.create(IMedia.class);
                TypedFile mediaTypedFile = null;
                Map<String, TypedFile> mediaList = new HashMap<String, TypedFile>();
                for (String path : MediasPaths) {
                    mediaTypedFile = new TypedFile("multipart/form-data", new File(path));

                    mediaList.put(path, mediaTypedFile);
                }

                media.MediaUpload(mediaList, new Callback<String>() {
                    @Override
                    public void success(String s, Response response) {
                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            MediasPaths = null;
            MediasPaths = Helper.GetAllPath(getApplicationContext(),data);
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //SomeThing();
        }
    }

}

package com.test.my.videoviewtest;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_VIDEO_CAPTURE = 1;
    private static final String TAG = "CaptureTest";
    private final ArrayList<File> mTempFiles = new ArrayList<>();
    private Uri mVideoUri;
    private VideoView mVideoView;
    private Button mPlayButton;
    private Button mHackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_simple);

        Button recapButton = (Button) findViewById(R.id.recapButton);
        recapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View aView) {
                capture();
            }
        });

        mVideoView = (VideoView)findViewById(R.id.videoView);
        mPlayButton = (Button)findViewById(R.id.playButton);
        mPlayButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View aView) {
                if (mVideoView != null) mVideoView.start();
            }
        });

        findViewById(R.id.changeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground();
            }
        });

        mHackButton = (Button)findViewById(R.id.hackButton);
        mHackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //MainDialog.create(mVideoUri).show(getFragmentManager(), "PlayVideoDialogFragment");
                new SurfaceDialog().show(getFragmentManager(), "SurfaceDialog");
            }
        });


        Uri uri = getFileUriIfExists("video.mp4");
        if (uri != null) {
            mVideoUri = uri;
            initVideo();
        }
    }

    void changeBackground() {
        findViewById(R.id.frameView).setBackgroundColor(new Random().nextInt(0xffffff) + 0xff000000);
    }

    private void initVideo() {
        if (mVideoView != null) {
            mVideoView.setVisibility(View.VISIBLE);
            mVideoView.setVideoURI(mVideoUri);
            mPlayButton.setVisibility(View.VISIBLE);
            mHackButton.setVisibility(View.VISIBLE);
        }

    }

    private Uri getFileUriIfExists(String fileName) {
        final File file = new File(getFilesDir() + File.separator + "images"
                + File.separator + fileName);
        if (!file.exists()) return null;
        return FileProvider.getUriForFile(this,
                "com.test.my.videoviewtest.fileprovider", file);
    }


    private Uri getTempUri(String fileName) {
        final File file = new File(getFilesDir() + File.separator + "images"
                + File.separator + fileName);
        file.getParentFile().mkdirs(); //if the folder doesn't exists it is created
        mTempFiles.add(file);
        return FileProvider.getUriForFile(this,
                "com.test.my.videoviewtest.fileprovider", file);
    }


    private void capture() {
        Intent captureVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        mVideoUri = getFileUri();
        captureVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mVideoUri);
        if (captureVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(captureVideoIntent, REQUEST_VIDEO_CAPTURE);
        } else {
            Log.e(TAG, "Capture video intent could not be resolved in managed profile.");
            finish();
        }
    }

    private Uri getFileUri() {
        return getTempUri("video.mp4");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                initVideo();
                MainDialog.create(mVideoUri).show(getFragmentManager(), "PlayVideoDialogFragment");
            } else {
                // Failed capturing video.
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (! isChangingConfigurations()) cleanUpTempUris();
        super.onDestroy();
    }

    private void cleanUpTempUris() {
        for (File file : mTempFiles) {
            file.delete();
        }
    }
}

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

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_VIDEO_CAPTURE = 1;
    private static final String TAG = "CaptureTest";
    private final ArrayList<File> mTempFiles = new ArrayList<>();
    private Uri mVideoUri;
    private VideoView mVideoView;
    private Button mPlayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                findViewById(R.id.frameView).setBackgroundColor(Color.GRAY);
            }
        });

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
        mVideoUri = getTempUri("video.mp4");
        captureVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mVideoUri);
        if (captureVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(captureVideoIntent, REQUEST_VIDEO_CAPTURE);
        } else {
            Log.e(TAG, "Capture video intent could not be resolved in managed profile.");
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                MainDialog.create(mVideoUri).show(getFragmentManager(), "PlayVideoDialogFragment");
                if (mVideoView != null) {
                    mVideoView.setVisibility(View.VISIBLE);
                    mVideoView.setVideoURI(mVideoUri);
                    mPlayButton.setVisibility(View.VISIBLE);
                }
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

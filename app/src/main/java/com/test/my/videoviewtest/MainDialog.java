package com.test.my.videoviewtest;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.test.my.videoviewtest.views.VideoView;

/**
 * @author Andrey Kulik (Andrey.Kulik@ascom.com).
 */

public class MainDialog extends DialogFragment {
    private static final String KEY_VIDEO_URI = "key_video_uri";
    private VideoView mVideoView;
    private Button mPlayButton;
    private View mFrameView;

    public static MainDialog create(Uri uri) {
        MainDialog dialog = new MainDialog();
        Bundle args = new Bundle();
        args.putParcelable(KEY_VIDEO_URI, uri);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_main);

        mVideoView = (VideoView)dialog.findViewById(R.id.videoView);
        mFrameView = dialog.findViewById(R.id.frameView);

        Drawable bd = mVideoView.getBackground();
        if (bd == null) {
            //mVideoView.setBackgroundColor(0);
        }

        mPlayButton = (Button)dialog.findViewById(R.id.playButton);
        mPlayButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View aView) {
                mVideoView.start();
            }
        });
        dialog.findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View aView) {
                dismiss();
            }
        });

        dialog.setTitle("Video test dialog");

        Uri uri = getArguments().getParcelable(KEY_VIDEO_URI);
        mVideoView.setVisibility(View.VISIBLE);
        mVideoView.setVideoURI(uri);

        Button playButton = (Button) dialog.findViewById(R.id.playButton);
        playButton.setVisibility(View.VISIBLE);
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mVideoView.start();
            }
        });

        dialog.findViewById(R.id.changeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().findViewById(R.id.frameView).setBackgroundColor(Color.RED);
            }
        });


        return dialog;
    }
}

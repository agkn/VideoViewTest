package com.test.my.videoviewtest;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.test.my.videoviewtest.views.VideoView;

import java.util.Random;

/**
 * @author Andrey Kulik (Andrey.Kulik@ascom.com).
 */

public class SurfaceDialog extends DialogFragment implements SurfaceHolder.Callback {
    private SurfaceView mSurfaceView;
    private Thread mThread;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_surface);

        dialog.findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dialog.findViewById(R.id.changeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeBackground();
            }
        });
        SurfaceView surface = mSurfaceView = (SurfaceView) dialog.findViewById(R.id.videoView);
        surface.getHolder().addCallback(this);
        surface.getHolder().setSizeFromLayout();
        return dialog;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startDrawing(holder);
    }

    private void startDrawing(final SurfaceHolder aSurfaceHolder) {
        mThread = new Thread() {
            @Override
            public void run() {
                final Surface surface = aSurfaceHolder.getSurface();
                final Random rnd = new Random();
                try {
                    while (!isInterrupted()) {
                        Canvas canvas = surface.lockCanvas(aSurfaceHolder.getSurfaceFrame());
                        canvas.drawRGB(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
                        surface.unlockCanvasAndPost(canvas);
                        sleep(500);
                    }
                } catch (InterruptedException aE) {
                    aE.printStackTrace();
                }
            }
        };
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }
    }
}

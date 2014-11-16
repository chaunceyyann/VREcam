package com.example.campre;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class CamPre extends SurfaceView implements SurfaceHolder.Callback {
    private static boolean DEBUGGING = true;
    private static final String LOG_TAG = "CameraPreviewSample";
    protected Activity mActivity;
   
    protected Camera mCamera;
    protected Camera nCamera;
    protected Camera.Size mPreviewSize;
    protected Camera.Size mPictureSize;
    private int mSurfaceChangedCallDepth = 0;
    private int mCameraId;
    private int mCenterPosX = -1;
    private int mCenterPosY;
    
    SurfaceHolder mHolder;

    public CamPre(MainActivity activity) throws IOException {
        super(activity);
        this.safeCameraOpen(0);
        //addView(mSurfaceView);
        Log.d(LOG_TAG, "00000000");
        
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        this.surfaceCreated(mHolder);
        mCamera.setDisplayOrientation(90);
    	mCamera.startPreview();

    }
    

	private boolean safeCameraOpen(int id) {
		Log.d(LOG_TAG, "111111111");
        boolean qOpened = false;
        String fff = String.valueOf(Camera.getNumberOfCameras());
        try {
            releaseCameraAndPreview();
            mCamera = Camera.open(id);
            Log.d(LOG_TAG, "222222222");
            requestLayout();
            qOpened = (mCamera != null);
        } catch (Exception e) {
            Log.d(LOG_TAG, fff);
            e.printStackTrace();
        }

        return qOpened;    
    }
  
    
    private void releaseCameraAndPreview() {
    	Log.d(LOG_TAG, "55555555");
        //mPreview.setCamera(null);
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
    
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
        	Log.d(LOG_TAG, "33333333");
            mCamera.setPreviewDisplay(mHolder);
        } catch (IOException e) {
            mCamera.release();
            mCamera = null;
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    	Camera.Parameters parameters = mCamera.getParameters();
    	Log.d(LOG_TAG, "444444");
        parameters.setPreviewSize(1280, 720);
        Log.d(LOG_TAG, "444445");
        requestLayout();
        Log.d(LOG_TAG, "444446");
        mCamera.setParameters(parameters);
        Log.d(LOG_TAG, "444447");
        // Important: Call startPreview() to start updating the preview surface.
        // Preview must be started before you can take a picture.
        mCamera.startPreview();
    }
    
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Surface will be destroyed when we return, so stop the preview.
        if (mCamera != null) {
            // Call stopPreview() to stop updating the preview surface.
            mCamera.stopPreview();
        }
    }
   
    
}

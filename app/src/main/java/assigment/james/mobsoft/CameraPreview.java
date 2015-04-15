package assigment.james.mobsoft;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import java.io.IOException;

/**
 * Created by james on 11/04/15.
 *
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private Camera camera;
    SurfaceHolder holder;

    public CameraPreview(Context context, Camera _camera) {
        super(context);

        camera = _camera;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        holder = getHolder();
        holder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    @Override
    public void surfaceCreated(SurfaceHolder _holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            camera.setPreviewDisplay(_holder);
            camera.startPreview();
        } catch (IOException e) {
            android.util.Log.e(VIEW_LOG_TAG, "Error setting camera preview: " + e.getMessage());
        }
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (holder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e){
        }
        // start preview with new settings
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();

        } catch (Exception e){
            android.util.Log.e(VIEW_LOG_TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
}
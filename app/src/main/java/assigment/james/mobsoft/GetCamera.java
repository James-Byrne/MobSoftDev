package assigment.james.mobsoft;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;
import android.hardware.camera2.CameraManager;

import java.io.IOException;
import java.util.List;

/**
 * Created by james on 11/04/15.
 *
 */
public class GetCamera {

    private static Camera camera;

    public static Camera getCamera() {
        try {
            camera = Camera.open();
        } catch (Exception e) {
            Log.e("Journal : ", "failed to open Camera @ CameraHandler.open()");
            e.printStackTrace();
        }
        return camera;
    }
}
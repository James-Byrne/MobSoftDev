package assigment.james.mobsoft;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Activity_Camera extends ActionBarActivity {


    Camera camera;
    CameraPreview cameraPreview;
    private Uri fileUri;

    // Activity request codes
    private static final int IMAGE_REQUEST_CODE = 100;
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_VIDEO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_camera);

        if(!checkCameraExists(this)){
            Toast toast = Toast.makeText(this,
                    "Error : No Camera Detected",
                    Toast.LENGTH_LONG);
            toast.show();
        } else {
            // Get an instance of the camera
            camera = GetCamera.getCamera();
            cameraPreview = new CameraPreview(this, camera);

            Bundle bundle = getIntent().getExtras();
            runCamera(bundle.getInt("MEDIA_TYPE"));
        }
    }

    public void runCamera(Integer TYPE){
        if (TYPE == TYPE_IMAGE){
            captureImage();
        } else if (TYPE == TYPE_VIDEO) {
            captureVideo();
        } else {
            Toast toast = Toast.makeText(this, "WRONG MEDIA TYPE", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Check if the device has a camera
     */
    private Boolean checkCameraExists(Context context){
        return context.getPackageManager().
                hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private void captureImage(){
        Intent intent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
        fileUri = getFileUri(TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        startActivityForResult(intent, TYPE_IMAGE);
    }

    private void captureVideo(){
        Intent intent = new Intent((MediaStore.ACTION_VIDEO_CAPTURE));
        fileUri = getFileUri(TYPE_VIDEO);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        startActivityForResult(intent, TYPE_VIDEO);
    }


    private static Uri getFileUri(int fileType){

        File mediaDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Journal App");

        if(!mediaDir.exists()){
            if (! mediaDir.mkdirs()){
                android.util.Log.e("journal", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File file;

        if(fileType == TYPE_IMAGE){
            file = new File(mediaDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if(fileType == TYPE_VIDEO) {
            file = new File(mediaDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return Uri.fromFile(file);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == IMAGE_REQUEST_CODE){
            if(resultCode == RESULT_OK){
            } else if (resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(),
                        "Image capture cancelled", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == RESULT_OK) {
        } else if (resultCode == RESULT_CANCELED) {
            // user cancelled recording
            Toast.makeText(getApplicationContext(),
                    "Video recording cancelled", Toast.LENGTH_SHORT)
                    .show();
        } else {
            // failed to record video
            Toast.makeText(getApplicationContext(),
                    "Failed to record video", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back) {
            Intent intent = new Intent(this, Activity_Get_Log.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

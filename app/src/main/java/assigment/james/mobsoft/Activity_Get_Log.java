package assigment.james.mobsoft;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

/**
 * Created by james
 *
 * This class manages all activities that occur
 * during the viewing/editing of a log
 *
 * Functionality
 * - Display a Log
 * - Edit the text of a Log
 * - Attach a photo/video file
 */

public class Activity_Get_Log extends ActionBarActivity {


    EditText edTitle;
    EditText edObj;
    EditText edCon;
    EditText edArr;
    EditText edRev;

    Log log;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int SELECT_PICTURE = 2;
    private String selectedImagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_log);

        edTitle = (EditText)findViewById(R.id.editText_Title);
        edObj = (EditText)findViewById(R.id.editText_Obj);
        edCon = (EditText)findViewById(R.id.editText_Con);
        edArr = (EditText)findViewById(R.id.editText_Arr);
        edRev = (EditText)findViewById(R.id.editText_Rev);

        loadLog();
    }

    /**
     * Populate the screen with the info from
     * the log passed to this screen
     */
    public void loadLog(){
        Intent intent = getIntent();
        log = (Log)intent.getSerializableExtra("Log");

        edTitle.setText(log.getTitle());
        edObj.setText(log.getObjective());
        edCon.setText(log.getCondition());
        edArr.setText(log.getArr_shot());
        edRev.setText(log.getReview());
    }

    public void back() {
        Intent intent = new Intent(this, Activity_List_Notes.class);
        startActivity(intent);
    }

    /**
     * When the save button is pressed create a new log
     */
    public void save() {
        LogHandler logHandler = new LogHandler(this);
        logHandler.open();

        if (edTitle.getText().toString().equals("")) {
            Toast toast = Toast.makeText(this, "Add Title", Toast.LENGTH_LONG);
            toast.show();
        } else if (logHandler.exists(log.getID())) {
            logHandler.editLog(
                    log.getID(),
                    edTitle.getText().toString(),
                    edObj.getText().toString(),
                    edCon.getText().toString(),
                    edArr.getText().toString(),
                    edRev.getText().toString(),
                    log.getImage(),
                    " _id = "
            );
            if (logHandler.exists(log.getID())) {
                Toast toast = Toast.makeText(this, "Log edited", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(this, "ERROR : Log Not Edited", Toast.LENGTH_LONG);
                toast.show();
            }
        } else {
            if (log.getImage() == null) {
                log = logHandler.createLog(
                        edTitle.getText().toString(),
                        edObj.getText().toString(),
                        edCon.getText().toString(),
                        edArr.getText().toString(),
                        edRev.getText().toString());

            } else {
                log = logHandler.createLog(
                        edTitle.getText().toString(),
                        edObj.getText().toString(),
                        edCon.getText().toString(),
                        edArr.getText().toString(),
                        edRev.getText().toString(),
                        log.getImage()
                );
            }
                if (logHandler.exists(log.getID())) {
                    Toast toast = Toast.makeText(this, "Log Saved", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(this, "ERROR : Log Not Saved", Toast.LENGTH_LONG);
                    toast.show();
                }
        }
        logHandler.close();
    }
    /**
     * When the delete button is pressed delete the
     * current log
     */
    public void delete(Log log){
        LogHandler logHandler = new LogHandler(this);
        logHandler.open();
        logHandler.deleteLog(log);

        if(!logHandler.exists(log.getID())){
            Toast toast = Toast.makeText(this, "Log Deleted", Toast.LENGTH_SHORT);
            toast.show();
        }else {
            Toast toast = Toast.makeText(this, "ERROR : Log Not Deleted", Toast.LENGTH_LONG);
            toast.show();
        }
        logHandler.close();

        Intent intent = new Intent(this, Activity_List_Notes.class);
        startActivity(intent);
    }

    public void getPicture() {
        Intent getPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (getPicture.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(getPicture, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void displayImage() {

        if (log.getImage() == null) {
            Toast toast = Toast.makeText(this, "No Images attached to Log", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Intent intent = new Intent(this, Activity_Display_Image.class);
            intent.putExtra("Image", log.getImage());
            startActivity(intent);
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                bitToByte((Bitmap)extras.get("data"));

            } else if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                bitToByte(getPath(selectedImageUri));
            }
        }
    }

    private Boolean bitToByte(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);

        log.setImage(outputStream.toByteArray());

        if(log.getImage()!= null){
            Toast toast = Toast.makeText(this,
                    "Image added to Log",
                    Toast.LENGTH_SHORT);
            toast.show();
            return true;
        } else {
            Toast toast = Toast.makeText(
                    this,
                    "Failed to add image to Log",
                    Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
    }

    private Boolean bitToByte(String imagePath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        return bitToByte(BitmapFactory.decodeFile(imagePath, options));
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the
        // action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_get_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_back:
                back();
                return true;
            case R.id.action_new_photo:
                getPicture();
                return true;
            case R.id.action_open_gallery:
                openGallery();
                return true;
            case R.id.action_save:
                save();
                return true;
            case R.id.action_delete:
                delete(log);
                return true;
            case R.id.action_display_image:
                displayImage();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

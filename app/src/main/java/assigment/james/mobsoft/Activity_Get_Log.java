package assigment.james.mobsoft;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

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

    /**
     * When the save button is pressed create a new log
     */
    public void save(){
        LogHandler logHandler = new LogHandler(this);
        logHandler.open();
        logHandler.createLog(
                edTitle.getText().toString(),
                edObj.getText().toString(),
                edCon.getText().toString(),
                edArr.getText().toString(),
                edRev.getText().toString()
        );
        logHandler.close();

        Toast toast = Toast.makeText(this, "Log Saved", Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * When the delete button is pressed delete the
     * current log
     */
    public void delete(Log log){
        LogHandler logHandler = new LogHandler(this);
        logHandler.open();
        logHandler.deleteLog(log);
        logHandler.close();

        Toast toast = Toast.makeText(this, "Log Deleted", Toast.LENGTH_SHORT);
        toast.show();

        Intent intent = new Intent(this, Activity_List_Notes.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_get_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_attach_photo:
                // take photo
                return true;
            case R.id.action_save:
                save();
                return true;
            case R.id.action_delete:
                delete(log);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package assigment.james.mobsoft;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


public class Activity_List_Notes extends ActionBarActivity {

    public ListView listView;
    public LogHandler logHandler = new LogHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_notes);

        listView=(ListView)findViewById(R.id.listView);
        // Populates the list
        populateList();
    }

    /**
     * Populates the list shown on the first screen
     * with a list of all logs that the user has
     * created.
     */
    public void populateList(){
        // Create an instance of LogHandler and open the
        // database for writing
        logHandler.open();

        // Get all logs the user has created
        final List<Log> values = logHandler.getAllLogs();

        ArrayAdapter<Log> adapter = new ArrayAdapter<Log>(this,
                android.R.layout.simple_list_item_1, values);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editLog(values.get(position));
            }
        });

        logHandler.close();
    }

    public void editLog(Log log){
        Intent intent = new Intent(this, Activity_Get_Log.class);
        intent.putExtra("Log", log);
        startActivity(intent);
    }

    public void newLog(){
        Intent intent = new Intent(this, Activity_Get_Log.class);
        Log log = new Log();
        intent.putExtra("Log", log);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__list__notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new_event) {
            newLog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package assigment.james.mobsoft;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class Activity_Display_Image extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        Bundle bundle = getIntent().getExtras();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bundle.getByteArray("Image"), 0, bundle.getByteArray("Image").length);

        ImageView imageView = (ImageView) findViewById(R.id.imageView_Display);
        imageView.setImageBitmap(bitmap);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_display_image, menu);
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
            //Log log = getIntent().getExtras();
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
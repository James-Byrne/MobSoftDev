package assigment.james.mobsoft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static assigment.james.mobsoft.R.layout.listview_rows;

/**
 * Created by james on 10/04/15.
 *
 */
public class ListLogsAdapter extends ArrayAdapter<Log> {
    private final List<Log> logs;
    private final Context context;

    public ListLogsAdapter(Context context, List<Log> logs) {
        super(context, listview_rows, logs);
        this.context = context;
        this.logs = logs;
    }

    public View getView(int position, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listview_rows, viewGroup, false);

        TextView textView = (TextView) row.findViewById(R.id.textView_List_Title);

        textView.setText(logs.get(position).getTitle());

        return row;
    }
}

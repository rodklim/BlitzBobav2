package blitzboba.blitzboba;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import blitzboba.blitzbobav2.R;

/**
 * Created by Rodrigo on 10/14/2017.
 */

public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.EventsListViewHolder> {

    public Context context;
    ArrayList<String> eventList;

    public EventsListAdapter(Context context, ArrayList<String> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @Override
    public EventsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.event_list_item, parent, false);
        return new EventsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventsListViewHolder holder, int position) {
        String event = eventList.get(position);
        TextView eventName = holder.eventName;
        eventName.setText(event);
        holder.eventName.setText(event);
        holder.eventName.setTextSize(15);
        holder.eventName.setTypeface(Typeface.DEFAULT);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class EventsListViewHolder extends RecyclerView.ViewHolder {

        TextView eventName;

        public EventsListViewHolder(View itemView) {
            super(itemView);
            eventName = (TextView) itemView.findViewById(R.id.event_name);

        }
    }


}

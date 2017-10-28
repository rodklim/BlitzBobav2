package blitzboba.blitzboba;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import blitzboba.blitzbobav2.R;

/**
 * Created by Rodrigo on 10/14/2017.
 */

public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.EventsListViewHolder> {

    public Context context;
    List<CalendarDataModel> calendarDataModelList;
    ArrayList<Integer>  arrayList = new ArrayList<>();
    public OnItemClickListener mItemClickListener;
    private Integer [] images = {R.drawable.bettyanddrink, R.drawable.bettythetruck2,
            R.drawable.bettythetruck, R.drawable.bettyandcrewdance2, R.drawable.mattandbetty};

    public EventsListAdapter(Context context, List<CalendarDataModel> calendarDataModelList) {
        this.context = context;
        this.calendarDataModelList = calendarDataModelList;
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
        CalendarDataModel calendarDataModel = calendarDataModelList.get(position);
        holder.eventDateAndName.setText(calendarDataModel.getLocationTitle());
        holder.eventDateAndName.setText(calendarDataModel.getDateWithNoYear() + " " + calendarDataModel.getLocationTitle());
        holder.eventTime.setText(calendarDataModel.getStartAndEndTime());
        holder.eventLocationSubtitle.setText(calendarDataModel.getLocationSubtitle());
        holder.eventDateAndName.setTextSize(15);
        holder.eventDateAndName.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/ARCADE_N.TTF"));
        holder.eventTime.setTextSize(15);
        holder.eventTime.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/ARCADE_N.TTF"));
        holder.eventLocationSubtitle.setTextSize(15);
        holder.eventLocationSubtitle.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/ARCADE_N.TTF"));

        Glide.with(context).load(images[position]).into(holder.eventImageView);
//        holder.eventImageView.setImageResource(images[position]);
//        Glide.with(context).load(images[position]).into(holder.eventImageView);
//        Glide.with(context).load(R.drawable.mangosmoothie).into(holder.eventImageView);
    }

    @Override
    public int getItemCount() {
        return calendarDataModelList.size();
    }

    public class EventsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView eventDateAndName;
        TextView eventTime;
        TextView eventLocationSubtitle;
        ImageView eventImageView;

        public EventsListViewHolder(final View itemView) {
            super(itemView);
            eventDateAndName = (TextView) itemView.findViewById(R.id.event_date_and_name_textView);
            eventTime = (TextView) itemView.findViewById(R.id.event_time_textView);
            eventLocationSubtitle = (TextView) itemView.findViewById(R.id.event_location_subTitle_textView);
            eventImageView = (ImageView) itemView.findViewById(R.id.events_imageView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(eventImageView, images[getAdapterPosition()],getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(ImageView view, int imageID, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}

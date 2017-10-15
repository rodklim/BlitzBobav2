package blitzboba.blitzboba;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ImageSliderViewPagerAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import blitzboba.blitzbobav2.R;

/**
 * Created by Rodrigo on 10/31/2016.
 */
public class Events extends Fragment {
    TextView textView;
    TextView eventsTextView;
    ViewPager viewPager;
    RecyclerView recyclerView;
    EventsListAdapter eventsListAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    //TODO finish Contact Us part of page
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.events,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.events_recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setStackFromEnd(false);
        eventsListAdapter = new EventsListAdapter(getContext(),getEventsList());
        textView = (TextView) v.findViewById(R.id.textView);
        textView.setTypeface(Typeface.DEFAULT);

        eventsTextView = (TextView) v.findViewById(R.id.events_title_textView);
        eventsTextView.setText("Events");
        eventsTextView.setTextSize(25);
        eventsTextView.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));


        viewPager = (ViewPager) v.findViewById(R.id.viewPager);

        ImageSliderViewPagerAdapter viewPagerAdapter = new ImageSliderViewPagerAdapter(getContext());

        viewPager.setAdapter(viewPagerAdapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 7000, 7000);

        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(eventsListAdapter);

        return v;
    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() == 0) {
                            viewPager.setCurrentItem(1);
                        } else if (viewPager.getCurrentItem() == 1) {
                            viewPager.setCurrentItem(2);
                        } else {
                            viewPager.setCurrentItem(0);
                        }

                    }
                });
            }
        }
    }

    public ArrayList<String> getEventsList() {
        //TODO somehow host events on webpage
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("10/30 UNLV 11 AM - 3 PM RLL");
        arrayList.add("10/31 UNLV 11 AM - 3 PM RLL");
        arrayList.add("11/1 UNLV 11 AM - 3 PM RLL");
        arrayList.add("11/2 UNLV 11 AM - 3 PM RLL");
        arrayList.add("11/3 First Friday 7 PM - 11 PM Liquor World");

        return arrayList;
    }
}

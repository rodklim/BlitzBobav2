package blitzboba.blitzboba;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ImageSliderViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import blitzboba.blitzbobav2.R;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by Rodrigo on 10/31/2016.
 */
public class HomeFragment extends Fragment implements CalendarContract.View {
    TextView textView;
    TextView eventsTextView;
    TextView aboutTitleTextView;
    Button contactUsButton;
    ViewPager viewPager;
    RecyclerView recyclerView;
    EventsListAdapter eventsListAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    public static final String CALENDAR = "Calendar";
    public static final String FONT_NAME = "fonts/ARCADE_N.TTF";

    //TODO finish Contact Us part of page
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(getContext().getCacheDir(), cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();
        View v =inflater.inflate(R.layout.home_fragment,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.events_recyclerView);
        textView = (TextView) v.findViewById(R.id.textView);
        eventsTextView = (TextView) v.findViewById(R.id.events_title_textView);
        aboutTitleTextView = (TextView) v.findViewById(R.id.about_title_textView);
        viewPager = (ViewPager) v.findViewById(R.id.viewPager);
        contactUsButton = (Button) v.findViewById(R.id.contact_us_button);
        final DataRequest dataRequest = new DataRequest(this);
//        dataRequest.getCalendarList();
        dataRequest.loadCalendar(CALENDAR, okHttpClient);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setStackFromEnd(false);
        textView.setTypeface(Typeface.DEFAULT);
        eventsTextView.setText(getString(R.string.events));
        eventsTextView.setTextSize(18);
        eventsTextView.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
        eventsTextView.setTypeface(Typeface.createFromAsset(getContext().getAssets(), FONT_NAME));
        aboutTitleTextView.setText(getString(R.string.About));
        aboutTitleTextView.setTextSize(18);
        aboutTitleTextView.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
        aboutTitleTextView.setTypeface(Typeface.createFromAsset(getContext().getAssets(), FONT_NAME));
        contactUsButton.setTypeface(Typeface.createFromAsset(getContext().getAssets(), FONT_NAME));
        contactUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFrag();
            }
        });
        ImageSliderViewPagerAdapter viewPagerAdapter = new ImageSliderViewPagerAdapter(getContext());
        viewPager.setAdapter(viewPagerAdapter);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 7000, 7000);
        return v;
    }

    @Override
    public void showCalendarEvents(final List<CalendarDataModel> calendarDataModelList) {
        eventsListAdapter = new EventsListAdapter(getContext(),calendarDataModelList);
        eventsListAdapter.setOnItemClickListener(new EventsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ImageView view, int imageID, int position) {
                Intent intent = EventDetailsActivity.createEventDetailsActivityIntent(getActivity(),
                        ViewCompat.getTransitionName(view),imageID,calendarDataModelList.get(position));
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(), view, ViewCompat.getTransitionName(view));
                startActivity(intent, options.toBundle());
            }
        });
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(eventsListAdapter);
        recyclerView.setNestedScrollingEnabled(false);
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

    private void showDialogFrag() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment prevFrag = fragmentManager.findFragmentByTag(ContactUsDialogFragment.FORM_DIALOG_FRAGMENT_TAG);
        if(prevFrag != null) {
            fragmentTransaction.remove(prevFrag);
        }
        fragmentTransaction.addToBackStack(null);

        DialogFragment dialogFragment = new ContactUsDialogFragment();
        dialogFragment.setTargetFragment(this, 1);
        dialogFragment.setCancelable(true);
        dialogFragment.show(fragmentTransaction, ContactUsDialogFragment.FORM_DIALOG_FRAGMENT_TAG);
    }
}

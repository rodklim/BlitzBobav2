package blitzboba.blitzboba;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import blitzboba.blitzbobav2.R;

/**
 * Created by Rodrigo on 10/16/2017.
 */

public class EventDetailsActivity extends AppCompatActivity implements OnMapReadyCallback{

    public static final String EXTRA_CONTACT = "EVENT_DETAILS";
    TextView eventDateAndName;
    TextView eventTime;
    TextView eventLocationSubtitle;
    TextView eventComments;
    ImageView eventImageView;
    CalendarDataModel calendarDataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail_activity);

        eventDateAndName = (TextView) findViewById(R.id.event_date_and_name_textView);
        eventTime = (TextView) findViewById(R.id.event_time_textView);
        eventLocationSubtitle = (TextView) findViewById(R.id.event_location_subTitle_textView);
        eventComments = (TextView) findViewById(R.id.event_comments_textView);
        eventDateAndName.setTextSize(15);
        eventDateAndName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ARCADE_N.TTF"));
        eventTime.setTextSize(15);
        eventTime.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ARCADE_N.TTF"));
        eventLocationSubtitle.setTextSize(15);
        eventLocationSubtitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ARCADE_N.TTF"));
        eventComments.setTextSize(15);
        eventComments.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ARCADE_N.TTF"));
        eventImageView = (ImageView) findViewById(R.id.events_imageView);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            calendarDataModel = (CalendarDataModel) bundle.getParcelable("EVENT_LIST");
            eventDateAndName.setText(calendarDataModel.getDateWithNoYear() + " " + calendarDataModel.getLocationTitle());
            eventTime.setText(calendarDataModel.getStartAndEndTime());
            eventLocationSubtitle.setText(calendarDataModel.getLocationSubtitle());
            eventComments.setText(calendarDataModel.getComments() + "\n\n" + (calendarDataModel.getOnlineOrderingAvail() ? "You can also order on the app ahead of time!" : ""));
            eventImageView.setTransitionName(bundle.getString("EVENT_TRANSITION_NAME"));
            int drawable = bundle.getInt("EVENT_IMAGE");

            supportPostponeEnterTransition();
            Glide.with(this).load(R.drawable.background_transparent).apply(new RequestOptions()
                    .dontAnimate().centerCrop().placeholder(drawable)).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    supportStartPostponedEnterTransition();
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    supportStartPostponedEnterTransition();
                    return false;
                }
            }).into(eventImageView);
        }

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Event Details");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(calendarDataModel != null) {
            LatLng location = getLocationFromAddress(calendarDataModel.getAddress());
            googleMap.addMarker(new MarkerOptions().position(location)
                    .title("UNLV")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.blitzbobamarker)));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.action_back) {
//            onBackPressed();
//            return true;
//        }
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
}

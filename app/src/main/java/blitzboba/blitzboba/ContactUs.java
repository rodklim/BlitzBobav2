package blitzboba.blitzboba;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import blitzboba.blitzbobav2.R;

/**
 * Created by Rodrigo on 10/31/2016.
 */
public class ContactUs extends Fragment {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private final static String yelpUri = "https://www.yelp.com/biz/blitz-boba-las-vegas-3";
    private final static String facebookUri = "https://facebook.com/blitzboba";
    private final static String websiteUri = "https://b litzboba.com";
    private final static String instagramUri = "https://instagram.com/blitzboba";
    private final static String twitterUri = "https://twitter.com/blitzboba";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contact_us,container,false);

        expandableListView = (ExpandableListView) v.findViewById(R.id.expandableListView);
        prepareListData();
        expandableListAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listDataChild);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setVisibility(View.VISIBLE);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {


                if (groupPosition == 0) {
                    switch (childPosition) {
                        case 0:
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(instagramUri)));
                            break;
                        case 1:
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(twitterUri)));
                            break;
                        case 2:
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUri)));
                            break;
                        case 3:
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUri)));
                            break;
                    }
                }
                if (groupPosition == 1) {
                    switch (childPosition) {
                        case 0:
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(yelpUri)));
                            break;
                    }
                }
                return false;
            }
        });

        return v;
    }



    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("Connect With Us");
        listDataHeader.add("Review");

        List<String> socialMedia = new ArrayList<String>();
        socialMedia.add("Instagram");
        socialMedia.add("Twitter");
        socialMedia.add("Facebook");
        socialMedia.add("Website");

        List<String> review = new ArrayList<String>();
        review.add("Yelp");

        listDataChild.put(listDataHeader.get(0), socialMedia);
        listDataChild.put(listDataHeader.get(1), review);

    }
}

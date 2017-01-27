package blitzboba.blitzboba;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import blitzboba.blitzbobav2.R;

/**
 * Created by Rodrigo on 10/18/2016.
 */
public class Menu extends Fragment {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menu,container,false);

        expandableListView = (ExpandableListView) v.findViewById(R.id.expandableListView);
        prepareListData();
        expandableListAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listDataChild);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setVisibility(View.VISIBLE);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                if (groupPosition == 2) {
                    switch (childPosition) {
                        case 0:

                            break;
                        case 1:

                            break;
                        case 2:

                            break;
                        case 3:

                            break;
                    }
                }
                return false;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return v;
    }



    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("Specialty");
        listDataHeader.add("Smoothie");
        listDataHeader.add("Slush");

        List<String> specialty = new ArrayList<String>();
        specialty.add("Horchata");
        specialty.add("OG Milk Tea");
        specialty.add("Oreo Milk Tea");
        specialty.add("Mojito Milk Tea");
        specialty.add("Coconut Thai Tea");
        specialty.add("Matcha Green Tea");
        listDataChild.put(listDataHeader.get(0), specialty);

        List<String> smoothie = new ArrayList<String>();
        smoothie.add("Oreo Taro");
        smoothie.add("Honeydew");
        smoothie.add("Strawberry");
        listDataChild.put(listDataHeader.get(1), smoothie);

        List<String> slush = new ArrayList<String>();
        slush.add("Mango");
        slush.add("Lychee");
        slush.add("Caramel Apple");
        listDataChild.put(listDataHeader.get(2), slush);

    }


}

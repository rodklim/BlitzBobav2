package blitzboba.blitzboba;


import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;

import com.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import blitzboba.blitzbobav2.R;

/**
 * Created by Rodrigo on 10/18/2016.
 */
public class Menu extends Fragment implements BobaContract.View {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private DatabaseReference mFirebaseDatabaseReference, childRef;
    private FirebaseRecyclerAdapter<BobaDrinks,BobaDrinksAdapter.BobaDrinksViewHolder> bobaDrinksFirebaseAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private Button filterButton;
    BobaDrinksAdapter adapter;
    private final CharSequence[] items = {"All","Vegan","Specialty","Smoothies"};
    private final ArrayList selectedItems = new ArrayList();
    private ArrayList<BobaDrinks> bobaDrinksArrayList = new ArrayList<>();
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private boolean isOrderingEnabled = false;
    List<BobaDrinks> bobaDrinksList;
    List<BobaDrinks> orderedBobaDrinks = new ArrayList<>();
    List<BobaDrinks> filteredDrinksList = new ArrayList<>();
    List<BobaDrinks> specialtyBobaDrinksList;
    List<BobaDrinks> smoothieBobaDrinksList;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.boba_drink_menu,container,false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.bobaDrinksRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setStackFromEnd(false);

        final MenuRequest menuRequest = new MenuRequest(this);
        menuRequest.loadDrinks("Drinks");
        checkBox1 = (CheckBox) v.findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox) v.findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) v.findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) v.findViewById(R.id.checkBox4);
        setCheckBoxRules(menuRequest);
        final FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOrderingEnabled) {
                    isOrderingEnabled = false;
                    fab.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
                } else {
                    isOrderingEnabled = true;
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.blitzbobalightning));
                    Snackbar.make(view, "Click on items you want to order!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(adapter);


        return v;
    }



    @Override
    public void showBobaDrinks(List<BobaDrinks> bobaDrinks) {
        bobaDrinksList = bobaDrinks;
        adapter = new BobaDrinksAdapter(getContext(),bobaDrinksList);
        adapter.setOnItemClickListener(new BobaDrinksAdapter.OnItemClickListener() {

            @Override
            public void onItemClickListener(View view, int position, BobaDrinks bobaDrinks) {
                if(isOrderingEnabled) {
                    orderedBobaDrinks.add(bobaDrinks);
                }
                Log.d("rlim", "" + bobaDrinks);
            }
        });
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(adapter);

        Log.d("Wooo","woooo");
    }

    @Override
    public void notifyDataSetChanged(List<BobaDrinks> bobaDrinksList) {
        adapter.setFilter(bobaDrinksList);
    }

    private void setCheckBoxRules(final MenuRequest menuRequest) {
        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuRequest.getNonSoldOutBobaDrinks();
                checkBox1.setChecked(true);
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
            }
        });

        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox1.isChecked()) {
                    checkBox1.setChecked(false);
                }
                if(!checkBox1.isChecked() && !checkBox3.isChecked() && !checkBox4.isChecked()) {
                    checkBox2.setChecked(true);
                }
                if (checkBox3.isChecked() && checkBox4.isChecked()) {
                    if(checkBox2.isChecked()) {
                        menuRequest.getVeganSpecialtyAndSmoothieDrinks();
                    } else {
                        menuRequest.getSpecialtyAndSmoothieDrinks();
                    }
                } else if(checkBox3.isChecked()) {
                    if(checkBox2.isChecked()) {
                        menuRequest.getVeganSpecialtyDrinks();
                    } else {
                        menuRequest.getSpecialtyBobaDrinks();
                    }
                } else if (checkBox4.isChecked()) {
                    if(checkBox2.isChecked()) {
                        menuRequest.getVeganSmoothieDrinks();
                    } else {
                        menuRequest.getSmoothieBobaDrinks();
                    }
                } else {
                    menuRequest.getVeganBobaDrinks();
                }
            }
        });

        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox1.isChecked()) {
                    checkBox1.setChecked(false);
                }
                if(!checkBox1.isChecked() && !checkBox2.isChecked() && !checkBox4.isChecked()) {
                    checkBox3.setChecked(true);
                }
                if (checkBox2.isChecked() && checkBox4.isChecked()) {
                    if(checkBox3.isChecked()) {
                        menuRequest.getVeganSpecialtyAndSmoothieDrinks();
                    } else {
                        menuRequest.getVeganSmoothieDrinks();
                    }
                } else if(checkBox2.isChecked()) {
                    if(checkBox3.isChecked()) {
                        menuRequest.getVeganSpecialtyDrinks();
                    } else {
                        menuRequest.getVeganBobaDrinks();
                    }
                } else if (checkBox4.isChecked()) {
                    if(checkBox3.isChecked()) {
                        menuRequest.getSpecialtyAndSmoothieDrinks();
                    } else {
                        menuRequest.getSmoothieBobaDrinks();
                    }
                } else {
                    menuRequest.getSpecialtyBobaDrinks();
                }
            }
        });

        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox1.isChecked()) {
                    checkBox1.setChecked(false);
                }
                if(!checkBox1.isChecked() && !checkBox2.isChecked() && !checkBox3.isChecked()) {
                    checkBox4.setChecked(true);
                }
                if (checkBox2.isChecked() && checkBox3.isChecked()) {
                    if(checkBox4.isChecked()) {
                        menuRequest.getVeganSpecialtyAndSmoothieDrinks();
                    } else {
                        menuRequest.getVeganSpecialtyDrinks();
                    }
                } else if(checkBox2.isChecked()) {
                    if(checkBox4.isChecked()) {
                        menuRequest.getVeganSmoothieDrinks();
                    } else {
                        menuRequest.getVeganBobaDrinks();
                    }
                } else if (checkBox3.isChecked()) {
                    if(checkBox4.isChecked()) {
                        menuRequest.getSpecialtyAndSmoothieDrinks();
                    } else {
                        menuRequest.getSpecialtyBobaDrinks();
                    }
                } else {
                    menuRequest.getSmoothieBobaDrinks();
                }
            }
        });
    }
}

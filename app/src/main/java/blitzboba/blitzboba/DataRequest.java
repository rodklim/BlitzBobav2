package blitzboba.blitzboba;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import blitzboba.blitzboba.retrofit.FirebaseRequestAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rodrigo on 4/4/2017.
 */

public class DataRequest implements BobaContract.Actions, CalendarContract.Actions {

    private BobaContract.View bobaView;
    private CalendarContract.View calendarView;
    private List<BobaDrinks> nonSoldOutBobaDrinks = new ArrayList<>();
    private List<BobaDrinks> soldOutBobaDrinks = new ArrayList<>();
    private List<BobaDrinks> specialtyBobaDrinks = new ArrayList<>();
    private List<BobaDrinks> smoothieBobaDrinks = new ArrayList<>();
    private List<BobaDrinks> veganBobaDrinks = new ArrayList<>();
    private List<BobaDrinks> veganSpecialtyBobaDrinks = new ArrayList<>();
    private List<BobaDrinks> veganSmoothieBobaDrinks = new ArrayList<>();
    private List<BobaDrinks> shownBobaDrinks = new ArrayList<>();
    private List<CalendarDataModel> calendarDataModelList = new ArrayList<>();

    public DataRequest(BobaContract.View bobaView) {
        this.bobaView = bobaView;
    }

    public DataRequest(CalendarContract.View calendarView) {
        this.calendarView = calendarView;
    }


    @Override
    public BobaDrinks loadDrinks(String url) {
        FirebaseRequestAPI firebaseRequestAPI = FirebaseRequestAPI.retrofit.create(FirebaseRequestAPI.class);
        Call<List<BobaDrinks>> call = firebaseRequestAPI.repoContributors(url);
        call.enqueue(new Callback<List<BobaDrinks>>() {
            @Override
            public void onResponse(Call<List<BobaDrinks>> call, Response<List<BobaDrinks>> response) {
                removeSoldOutDrinks(response.body());
                setSpecialtyAndSmoothieBobaList(nonSoldOutBobaDrinks);
                bobaView.showBobaDrinks(nonSoldOutBobaDrinks);
            }

            @Override
            public void onFailure(Call<List<BobaDrinks>> call, Throwable t) {
                //TODO handle failures
            }
        });

        return null;
    }

    @Override
    public CalendarDataModel loadCalendar(String url) {
        FirebaseRequestAPI firebaseRequestAPI = FirebaseRequestAPI.retrofit.create(FirebaseRequestAPI.class);
        Call<List<CalendarDataModel>> call = firebaseRequestAPI.getCalendar(url);
        call.enqueue(new Callback<List<CalendarDataModel>>() {
            @Override
            public void onResponse(Call<List<CalendarDataModel>> call, Response<List<CalendarDataModel>> response) {
               filterCalendarEvents(response.body());
            }

            @Override
            public void onFailure(Call<List<CalendarDataModel>> call, Throwable t) {
                //TODO handle failures
                Log.d("rlim", "):");
            }
        });
        return null;
    }

    private void removeSoldOutDrinks(List<BobaDrinks> bobaDrinks) {
        for (int i = 0; i < bobaDrinks.size(); i++) {
            if (bobaDrinks.get(i).isSoldOut()) {
                soldOutBobaDrinks.add(bobaDrinks.get(i));
                bobaDrinks.remove(i);
            } else {
                nonSoldOutBobaDrinks.add(bobaDrinks.get(i));
            }
        }
    }

    private void setSpecialtyAndSmoothieBobaList(List<BobaDrinks> bobaDrinks) {
        for(int i = 0; i < bobaDrinks.size(); i++) {
            if (bobaDrinks.get(i).getType().equalsIgnoreCase("Specialty")) {
                specialtyBobaDrinks.add(bobaDrinks.get(i));
                if(bobaDrinks.get(i).isVegan()) {
                    veganBobaDrinks.add(bobaDrinks.get(i));
                    veganSpecialtyBobaDrinks.add(bobaDrinks.get(i));
                }
            } else if (bobaDrinks.get(i).getType().equalsIgnoreCase("Smoothie")) {
                smoothieBobaDrinks.add(bobaDrinks.get(i));
                if(bobaDrinks.get(i).isVegan()) {
                    veganBobaDrinks.add(bobaDrinks.get(i));
                    veganSmoothieBobaDrinks.add(bobaDrinks.get(i));
                }
            }
        }
    }

    public void getNonSoldOutBobaDrinks() {
        nonSoldOutBobaDrinks.clear();
        nonSoldOutBobaDrinks.addAll(smoothieBobaDrinks);
        nonSoldOutBobaDrinks.addAll(specialtyBobaDrinks);
        changeShownDrinks(nonSoldOutBobaDrinks);
        bobaView.notifyDataSetChanged(shownBobaDrinks);
    }

    public void getSpecialtyBobaDrinks(){
        changeShownDrinks(specialtyBobaDrinks);
        bobaView.notifyDataSetChanged(shownBobaDrinks);
    }

    public void getSmoothieBobaDrinks() {
        changeShownDrinks(smoothieBobaDrinks);
        bobaView.notifyDataSetChanged(shownBobaDrinks);
    }

    public void getVeganBobaDrinks() {
        changeShownDrinks(veganBobaDrinks);
        bobaView.notifyDataSetChanged(shownBobaDrinks);
    }

    public void getVeganSpecialtyDrinks() {
        changeShownDrinks(veganSpecialtyBobaDrinks);
        bobaView.notifyDataSetChanged(shownBobaDrinks);
    }

    public void getVeganSmoothieDrinks() {
        changeShownDrinks(veganSmoothieBobaDrinks);
        bobaView.notifyDataSetChanged(shownBobaDrinks);
    }

    public void getVeganSpecialtyAndSmoothieDrinks() {
        List<BobaDrinks> bobaDrinkses = new ArrayList<BobaDrinks>();
        bobaDrinkses.addAll(veganSmoothieBobaDrinks);
        bobaDrinkses.addAll(veganSpecialtyBobaDrinks);
        changeShownDrinks(bobaDrinkses);
        bobaView.notifyDataSetChanged(bobaDrinkses);
    }

    public void getSpecialtyAndSmoothieDrinks() {
        List<BobaDrinks> bobaDrinkses = new ArrayList<BobaDrinks>();
        bobaDrinkses.addAll(smoothieBobaDrinks);
        bobaDrinkses.addAll(specialtyBobaDrinks);
        changeShownDrinks(bobaDrinkses);
        bobaView.notifyDataSetChanged(bobaDrinkses);
    }

    private void changeShownDrinks(List<BobaDrinks> bobaDrinksList) {
        shownBobaDrinks.clear();
        shownBobaDrinks.addAll(bobaDrinksList);
    }

    private void filterCalendarEvents(List<CalendarDataModel> calendarModelList) {
        Calendar c = Calendar.getInstance();
        Date today = c.getTime();
        long currentTimeinMilliseconds = System.currentTimeMillis();
        if(calendarModelList.size() <= 5) {
            calendarView.showCalendarEvents(calendarModelList);
        } else {
            int eventCount = 0;
            for(int i = 0; i < calendarModelList.size(); i++){
                Date date = new Date(calendarModelList.get(i).getDate());
                if(date.before(today)) {
                    return;
                } else {
                    if(eventCount < 5) {
                        calendarDataModelList.add(calendarModelList.get(i));
                    }
                    eventCount++;
                }
            }
            calendarView.showCalendarEvents(calendarDataModelList);
        }

    }

}

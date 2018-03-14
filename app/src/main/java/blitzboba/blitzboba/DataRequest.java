package blitzboba.blitzboba;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import blitzboba.blitzboba.retrofit.FirebaseRequestAPI;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rodrigo on 4/4/2017.
 */

public class DataRequest implements BobaContract.Actions, CalendarContract.Actions {

    private BobaContract.View bobaView;
    private CalendarContract.View calendarView;
    private LauncherContract.View launcherView;
    private List<BobaDrinks> nonSoldOutBobaDrinks = new ArrayList<>();
    private List<BobaDrinks> soldOutBobaDrinks = new ArrayList<>();
    private List<BobaDrinks> specialtyBobaDrinks = new ArrayList<>();
    private List<BobaDrinks> smoothieBobaDrinks = new ArrayList<>();
    private List<BobaDrinks> veganBobaDrinks = new ArrayList<>();
    private List<BobaDrinks> veganSpecialtyBobaDrinks = new ArrayList<>();
    private List<BobaDrinks> veganSmoothieBobaDrinks = new ArrayList<>();
    private List<BobaDrinks> shownBobaDrinks = new ArrayList<>();
    private List<CalendarDataModel> calendarDataModelList = new ArrayList<>();
    public static final String SPECIALTY = "Specialty";
    public static final String SMOOTHIE = "Smoothie";

    public DataRequest(BobaContract.View bobaView) {
        this.bobaView = bobaView;
    }

    public DataRequest(CalendarContract.View calendarView) {
        this.calendarView = calendarView;
    }

    public DataRequest(LauncherContract.View launcherView) {
        this.launcherView = launcherView;
    }

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    @Override
    public BobaDrinks loadDrinks(String url, final OkHttpClient okHttpClient) {
        Call<List<BobaDrinks>> call =  createRetrofit(url,okHttpClient);
        call.enqueue(new Callback<List<BobaDrinks>>() {
            @Override
            public void onResponse(Call<List<BobaDrinks>> call, Response<List<BobaDrinks>> response) {
                removeSoldOutDrinks(response.body());
                setSpecialtyAndSmoothieBobaList(nonSoldOutBobaDrinks);
//                loadCalendar("Calendar");
                bobaView.showBobaDrinks(nonSoldOutBobaDrinks);
            }

            @Override
            public void onFailure(Call<List<BobaDrinks>> call, Throwable t) {
                loadCalendar("Calendar", okHttpClient);
                //TODO handle failures
            }
        });

        return null;
    }

    @Override
    public CalendarDataModel loadCalendar(String url, OkHttpClient okHttpClient) {
        Call<List<CalendarDataModel>> call = createCalendarRetrofit(url,okHttpClient);
        call.enqueue(new Callback<List<CalendarDataModel>>() {
            @Override
            public void onResponse(Call<List<CalendarDataModel>> call, Response<List<CalendarDataModel>> response) {
               filterCalendarEvents(response.body());
//               launcherView.startMainActivity();
            }

            @Override
            public void onFailure(Call<List<CalendarDataModel>> call, Throwable t) {
                //TODO handle failures
                Log.d("rlim", "):");
//                launcherView.showErrorScreen();
            }
        });
        return null;
    }

    private void writeToCalendar(String eventNumber, CalendarDataModel calendarDataModel) {
        Call<CalendarDataModel> call = createRetrofit().setEventData(eventNumber,calendarDataModel);
        call.enqueue(new Callback<CalendarDataModel>() {
            @Override
            public void onResponse(Call<CalendarDataModel> call, Response<CalendarDataModel> response) {
                Log.d("rlim", "you can write");
            }

            @Override
            public void onFailure(Call<CalendarDataModel> call, Throwable t) {
                Log.d("rlim", "yeah...it didn't work");
            }
        });
    }

    private void writeToCalendarSpecific(String url) {
        Call<CalendarDataModel> call = createRetrofit().setEventData("6",new CalendarDataModel("","",1000,1000,true,"",""));
        call.enqueue(new Callback<CalendarDataModel>() {
            @Override
            public void onResponse(Call<CalendarDataModel> call, Response<CalendarDataModel> response) {
                Log.d("rlim", "you can write");
            }

            @Override
            public void onFailure(Call<CalendarDataModel> call, Throwable t) {
                Log.d("rlim", "yeah...it didn't work");
            }
        });
    }

    public void getCalendarList() {
        calendarView.showCalendarEvents(calendarDataModelList);
    }

    public void getBobaDrinks()
    {
        bobaView.showBobaDrinks(nonSoldOutBobaDrinks);
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
            if (bobaDrinks.get(i).getType().equalsIgnoreCase(SPECIALTY)) {
                specialtyBobaDrinks.add(bobaDrinks.get(i));
                if(bobaDrinks.get(i).isVegan()) {
                    veganSpecialtyBobaDrinks.add(bobaDrinks.get(i));
                }
            } else if (bobaDrinks.get(i).getType().equalsIgnoreCase(SMOOTHIE)) {
                smoothieBobaDrinks.add(bobaDrinks.get(i));
                if(bobaDrinks.get(i).isVegan()) {
                    veganSmoothieBobaDrinks.add(bobaDrinks.get(i));
                }
            }
        }
        nonSoldOutBobaDrinks.clear();
        nonSoldOutBobaDrinks.addAll(specialtyBobaDrinks);
        nonSoldOutBobaDrinks.addAll(smoothieBobaDrinks);
        veganBobaDrinks.addAll(veganSpecialtyBobaDrinks);
        veganBobaDrinks.addAll(veganSmoothieBobaDrinks);
    }

    public void getNonSoldOutBobaDrinks() {
        nonSoldOutBobaDrinks.clear();
        nonSoldOutBobaDrinks.addAll(specialtyBobaDrinks);
        nonSoldOutBobaDrinks.addAll(smoothieBobaDrinks);
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
        bobaDrinkses.addAll(veganSpecialtyBobaDrinks);
        bobaDrinkses.addAll(veganSmoothieBobaDrinks);
        changeShownDrinks(bobaDrinkses);
        bobaView.notifyDataSetChanged(bobaDrinkses);
    }

    public void getSpecialtyAndSmoothieDrinks() {
        List<BobaDrinks> bobaDrinkses = new ArrayList<BobaDrinks>();
        bobaDrinkses.addAll(specialtyBobaDrinks);
        bobaDrinkses.addAll(smoothieBobaDrinks);
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
        if(calendarModelList.size() <= 5) {
            calendarView.showCalendarEvents(calendarModelList);
//            calendarDataModelList.addAll(calendarModelList);
        } else {
            int eventCount = 0;
            for(int i = 0; i < calendarModelList.size(); i++){
                Date date = new Date(calendarModelList.get(i).getDate() + calendarModelList.get(i).getDuration());
                if(date.after(today)) {
                    if(eventCount < 5) {
                        calendarDataModelList.add(calendarModelList.get(i));
                    }
                    eventCount++;
                }
            }
            calendarView.showCalendarEvents(calendarDataModelList);
        }
    }

    private Call createRetrofit(String url, OkHttpClient okHttpClient) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(FirebaseRequestAPI.DATABASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(FirebaseRequestAPI.class).repoContributors(url);
    }

    private Call createCalendarRetrofit(String url, OkHttpClient okHttpClient) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(FirebaseRequestAPI.DATABASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(FirebaseRequestAPI.class).getCalendar(url);
    }

    private FirebaseRequestAPI createRetrofit() {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(FirebaseRequestAPI.DATABASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(FirebaseRequestAPI.class);
    }
}

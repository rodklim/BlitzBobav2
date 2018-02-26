package blitzboba.blitzboba.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import blitzboba.blitzboba.BobaDrinks;
import blitzboba.blitzboba.CalendarDataModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Rodrigo on 4/4/2017.
 */

public interface FirebaseRequestAPI {

    String DATABASE_URL = "https://blitz-boba.firebaseio.com/";
    @GET("{Drinks}.json")
    Call<List<BobaDrinks>> repoContributors(
            @Path("Drinks") String specialty);
    @GET("{Calendar}.json")
            Call<List<CalendarDataModel>> getCalendar(@Path("Calendar") String calendar);

    @PUT("/Calendar/{eventNumber}.json")
    Call<CalendarDataModel> setEventData(@Path("eventNumber") String s1, @Body CalendarDataModel calendarDataModel);
}

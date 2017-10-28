package blitzboba.blitzboba;

import java.util.List;

/**
 * Created by Rodrigo on 10/27/2017.
 */

public class CalendarContract {

    public interface View {
        void showCalendarEvents(List<CalendarDataModel> calendarDataModelList);
    }

    public interface Actions {
        CalendarDataModel loadCalendar(String url);
    }

}

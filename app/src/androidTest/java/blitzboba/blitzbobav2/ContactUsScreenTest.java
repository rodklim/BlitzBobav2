package blitzboba.blitzbobav2;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import blitzboba.blitzboba.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeRight;

/**
 * Created by Rodrigo on 1/26/2017.
 */
@RunWith(AndroidJUnit4.class)
public class ContactUsScreenTest {

    @Rule
    public ActivityTestRule<MainActivity> mContactUsTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void clickOnContactUs() throws Exception {
        onView(ViewMatchers.withId(R.id.tabs)).perform(swipeRight());
        onView(ViewMatchers.withId(R.id.tabs)).perform(swipeRight());
        onView(ViewMatchers.withId(R.id.tabs)).perform(swipeRight());
        onView(ViewMatchers.withId(R.id.tabs)).perform(swipeRight());
    }
}

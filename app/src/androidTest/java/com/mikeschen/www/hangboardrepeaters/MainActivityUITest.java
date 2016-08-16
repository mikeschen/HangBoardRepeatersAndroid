package com.mikeschen.www.hangboardrepeaters;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class MainActivityUITest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void buttonLaunchesOtherActivity() throws Exception {
        String otherActivityString = "Hang";
        onView(withId(R.id.startButton)).perform(click());
        onView(withText(otherActivityString)).check(matches(notNullValue()));
    }
}

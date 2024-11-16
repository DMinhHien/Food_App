package com.example.didong_foodapp.login;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.didong_foodapp.LoginActivity;
import com.example.didong_foodapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class LoginInstrumentedTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testSuccessfulLogin() {
        // Enter email and password
        onView(withId(R.id.Email)).perform(typeText("test@example.com"));
        onView(withId(R.id.Pass)).perform(typeText("password123"));

        // Click login button
        onView(withId(R.id.button_sign)).perform(click());

        // Verify MainActivity starts (this assumes MainActivity changes something observable)
        onView(withText("Welcome to MainActivity")).check(matches(isDisplayed())); // Adjust this based on your app's behavior
    }
}

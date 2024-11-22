package com.example.didong_foodapp.login;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.util.Log;
import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.didong_foodapp.LoginActivity;
import com.example.didong_foodapp.R;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class LoginParamInstrumentedTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    private final String email;
    private final String password;
    private final boolean isSuccessful;
    private final String expectedMessage;

    private View decorView;

    public LoginParamInstrumentedTest(String email, String password, boolean isSuccessful, String expectedMessage) {
        this.email = email;
        this.password = password;
        this.isSuccessful = isSuccessful;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"minhtri.com", "123456", false, "Authentication failed."},
                {"minhtri@gmail.com", "123", false, "Authentication failed."},
                {"", "123456", false, "Enter email"},
                {"minhtri123@gmail.com", "", false, "Enter password"},
                {"minhtri@gmail.com", "123456", true, "Login successful."},
        });
    }

    @Before
    public void setUp() {
        activityScenarioRule.getScenario().onActivity(
                activity -> decorView = activity.getWindow().getDecorView());
        FirebaseAuth.getInstance().signOut();
    }

    @Test
    public void testLogin() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Input email and password
        onView(withId(R.id.Email)).perform(clearText(), typeText(email), closeSoftKeyboard());
        onView(withId(R.id.Pass)).perform(clearText(), typeText(password), closeSoftKeyboard());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click login button
        onView(withId(R.id.button_sign)).perform(click());

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (!expectedMessage.isEmpty()) {
            if (isSuccessful) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                assert(auth.getCurrentUser() != null);
            } else {
                onView(withId(com.google.android.material.R.id.snackbar_text))
                        .check(matches(withText(expectedMessage)));
                assert(auth.getCurrentUser() == null);
                Log.d("TESTLOGIN", "Authentication failed: No user authenticated");
            }
        }
    }

    @After
    public void tearDown() {
        if(isSuccessful)
            FirebaseAuth.getInstance().signOut();
    }

    public View getDecorView() {
        return decorView;
    }

    public void setDecorView(View decorView) {
        this.decorView = decorView;
    }

}

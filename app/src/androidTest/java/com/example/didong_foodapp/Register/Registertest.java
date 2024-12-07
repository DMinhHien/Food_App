package com.example.didong_foodapp.Register;
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

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.RegistrationActivity;
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
public class Registertest {

    @Rule
    public ActivityScenarioRule<RegistrationActivity> activityScenarioRule =
            new ActivityScenarioRule<>(RegistrationActivity.class);

    private final String username;
    private final String email;
    private final String password;
    private final boolean isSuccessful;
    private final String expectedMessage;

    private View decorView;

    public Registertest(String username,String email, String password, boolean isSuccessful, String expectedMessage) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isSuccessful = isSuccessful;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"tuantrelt","tuantrett@gmail.com", "123456", false, "Authenciation failed"},
                {"tuantrelt","tuantre2@gmail.com", "abc456", true, "Account created"},
                {"tuan12@","tuantre2@gmail.com", "abc456", true, "Authenciation failed"},
                {"tuan12@","tuantre2@gmail.com", "12345", false, "Password must contain 6 characters or more"},
                {"123@add","123@tun", "@123abcdes", false, "Email is invalid"},
                {"tuan12@","123tuan@gma.com", "123456", false, "Email is invalid"},
                {"","tuantreltt3@gmail.com", "123456", true, "Enter Username"},
                {"tuantrelt","tuantreltt@gmail.co", "@", false, "Email is invalid"},
                {"tuan12@","", "@123abcdes", false, "Enter email"},
                {"123@add","tuantreltt@gmail.co", "", false, "Enter password"},
                {"tuan12@","", "", false, "Enter email"},
                {"tuanllllllllllllllllllllll","tuanqe12@gmail.com", "@", false, "Password must contain 6 characters or more"},
                {"tuantrelt","tuantre1311@gmail.com", "12345", false, "Password must contain 6 characters or more"},
                {"tuan12@","tuna2gmail.com", "123456", true, "Account created"},
                {"","", "", false, "Enter Username"},
        });
    }

    @Before
    public void setUp() {
        activityScenarioRule.getScenario().onActivity(
                activity -> decorView = activity.getWindow().getDecorView());
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            FirebaseAuth.getInstance().signOut();
    }

    @Test
    public void testRegister() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Input username, email and password
        onView(withId(R.id.user)).perform(clearText(), typeText(username), closeSoftKeyboard());
        onView(withId(R.id.Email)).perform(clearText(), typeText(email), closeSoftKeyboard());
        onView(withId(R.id.Pass)).perform(clearText(), typeText(password), closeSoftKeyboard());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click login button
        onView(withId(R.id.button_register)).perform(click());

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (!expectedMessage.isEmpty()) {
            if (isSuccessful) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                assert(auth.getCurrentUser() != null);
                Log.d("TESTREGISTER", "Authentication failed: No user authenticated");

            } else {
                onView(withId(com.google.android.material.R.id.snackbar_text))
                        .check(matches(withText(expectedMessage)));
                assert(auth.getCurrentUser() == null);
                Log.d("TESTREGISTER", "Authentication failed: No user authenticated");
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



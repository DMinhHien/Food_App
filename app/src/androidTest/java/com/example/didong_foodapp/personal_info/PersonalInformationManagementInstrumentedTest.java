package com.example.didong_foodapp.personal_info;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.rule.GrantPermissionRule;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.fragments.FoodFragment;
import com.example.didong_foodapp.util.ToastMatcher;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@RunWith(Parameterized.class)
public class PersonalInformationManagementInstrumentedTest {
    @Rule
    public GrantPermissionRule grantPermissionRule =
            GrantPermissionRule.grant(android.Manifest.permission.INTERNET);
    private final String nameInput;
    private final String phoneInput;
    private final String addressInput;
    private final boolean expectedResult;

    public PersonalInformationManagementInstrumentedTest
            (String name, String phone, String address, boolean expected) {
        this.nameInput = name;
        this.phoneInput = phone;
        this.addressInput = address;
        this.expectedResult = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testCases() {
        return Arrays.asList(new Object[][]{
                {"Minh Thuan", "091234567", "Linh Dong, Thu Duc, TPHCM", true},
                {"", "091234567", "Linh Dong, Thu Duc, TPHCM", false},
                {"Minh Thuan", "", "Linh Dong, Thu Duc, TPHCM", false},
                {"Minh Thuan", "091234567", "", false},
                {"Minh Thuan", "0912346", "Linh Dong, Thu Duc, TPHCM", false},
                {"Minh Thuan", "0912346as", "Linh Dong, Thu Duc, TPHCM", false},
        });
    }

    @Before
    public void setUp() {
        FirebaseAuth.getInstance().signOut();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword("minhtri@gmail.com", "123456");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testUpdateFunctionality() {
        FragmentScenario<FoodFragment> scenario
                = FragmentScenario.launchInContainer(
                        FoodFragment.class, null, R.style.Theme_DiDong_FoodApp);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.name)).perform(replaceText(nameInput));
        onView(withId(R.id.phone)).perform(replaceText(phoneInput));
        onView(withId(R.id.address)).perform(replaceText(addressInput));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.btnUpdate)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if (expectedResult) {
            onView(withId(R.id.name)).check(matches(withText(nameInput)));
            onView(withId(R.id.phone)).check(matches(withText(phoneInput)));
            onView(withId(R.id.address)).check(matches(withText(addressInput)));

            onView(withId(com.google.android.material.R.id.snackbar_text))
                    .check(matches(withText("Update Successfully")));

        } else {
            onView(withId(com.google.android.material.R.id.snackbar_text))
                    .check(matches(withText("Update Failed")));
        }


    }
}

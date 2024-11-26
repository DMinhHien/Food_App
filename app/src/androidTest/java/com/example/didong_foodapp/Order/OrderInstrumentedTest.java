package com.example.didong_foodapp.Order;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.GrantPermissionRule;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.fragments.LocationFragment;
import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;


@RunWith(Parameterized.class)
public class OrderInstrumentedTest {
    @Rule
    public GrantPermissionRule grantPermissionRule =
            GrantPermissionRule.grant(android.Manifest.permission.INTERNET);
    private final String nameInput;
    private final String phoneInput;
    private final String addressInput;
    private final boolean expectedResult;

    public OrderInstrumentedTest
            (String name, String phone, String address, boolean expected) {
        this.nameInput = name;
        this.phoneInput = phone;
        this.addressInput = address;
        this.expectedResult = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testCases() {
        return Arrays.asList(new Object[][]{
                {"Duong Trinh", "1234", "Binh Duong", true},
                {"", "1234", "Binh Duong", false},
                {"Duong Trinh", "1234", "", false},
                {"Duong Trinh", "", "Binh Duong", false}
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
                .signInWithEmailAndPassword("duongtx2@gmail.com", "123456");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testOrderFunctionality() {
        FragmentScenario<LocationFragment> scenario
                = FragmentScenario.launchInContainer(
                LocationFragment.class, null, R.style.Theme_DiDong_FoodApp);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(ViewMatchers.withId(R.id.recyclerLocation))
                .perform(actionOnItemAtPosition(0, click()));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.btnDatHang)).perform(ViewActions.scrollTo()).check(ViewAssertions.matches(isDisplayed()));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.recyclerMenu)) .perform(RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(R.id.detail_button_add)));
        onView(withId(R.id.recyclerMenu)) .perform(RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(R.id.detail_button_add)));
        onView(withId(R.id.btnDatHang)).perform(click());
        onView(withId(R.id.button_newOrder)).perform(click());
        onView(withId(R.id.name)).perform(replaceText(nameInput));
        onView(withId(R.id.phone)).perform(replaceText(phoneInput));
        onView(withId(R.id.address)).perform(replaceText(addressInput));
        onView(withId(R.id.btnXacnhan)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (expectedResult) {
            onView(withId(com.google.android.material.R.id.snackbar_text))
                    .check(matches(withText("Đặt hàng thành công!")));
        } else {
            onView(withId(com.google.android.material.R.id.snackbar_text))
                    .check(matches(withText("Vui lòng nhập đầy đủ thông tin!")));
        }

    }
    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isDisplayed(); // Chỉ áp dụng cho các View đang hiển thị
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View childView = view.findViewById(id);
                if (childView != null) {
                    childView.performClick();
                }
            }
        };
    }
}


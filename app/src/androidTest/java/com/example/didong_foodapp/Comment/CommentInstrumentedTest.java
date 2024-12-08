package com.example.didong_foodapp.Comment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.GrantPermissionRule;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Adapters.RecyclerLocation;
import com.example.didong_foodapp.ui.fragments.FoodFragment;
import com.example.didong_foodapp.ui.fragments.LocationFragment;
import com.example.didong_foodapp.util.ToastMatcher;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;



@RunWith(Parameterized.class)
public class CommentInstrumentedTest {
    @Rule
    public GrantPermissionRule grantPermissionRule =
            GrantPermissionRule.grant(android.Manifest.permission.INTERNET);
    private final String titleInput;
    private final String contentInput;
    private final String scoreInput;
    private final int expectedResult;

    public CommentInstrumentedTest
            (String title, String content, String score, int expected) {
        this.titleInput = title;
        this.contentInput = content;
        this.scoreInput = score;
        this.expectedResult = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testCases() {
        return Arrays.asList(new Object[][]{
                {"Delicious Food", "Should be visited again", "9", 1},
                {"", "Should be visited again", "9", 2},
                {"Delicious Food", "", "9", 3},
                {"Delicious Food", "Should be visited again", "", 4},
                {"Delicious Food", "Should be visited again", "11", 5}
        });
    }

    @Before
    public void setUp() {
//        if (FirebaseAuth.getInstance().getCurrentUser() != null)
//            FirebaseAuth.getInstance().signOut();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword("duongminhhien14@gmail.com", "123456");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testAddCommentFunctionality() {
        FragmentScenario<LocationFragment> scenario
                = FragmentScenario.launchInContainer(
                LocationFragment.class, null, R.style.Theme_DiDong_FoodApp);

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(ViewMatchers.withId(R.id.recyclerLocation))
                .perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.btnBinhLuan)).perform(click());
        onView(withId(R.id.editTextTitle)).perform(replaceText(titleInput));
        onView(withId(R.id.editTextComment)).perform(replaceText(contentInput));
        onView(withId(R.id.editTextScore)).perform(replaceText(scoreInput));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.txtDangBinhLuan)).perform(click());

        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (expectedResult==1) {
            onView(withId(com.google.android.material.R.id.snackbar_text))
                    .check(matches(withText("Đã đăng bình luận")));

        } else if (expectedResult==2){
            onView(withId(com.google.android.material.R.id.snackbar_text))
                    .check(matches(withText("Hãy nhập tiêu đề")));
        }
        else if (expectedResult==3){
            onView(withId(com.google.android.material.R.id.snackbar_text))
                    .check(matches(withText("Hãy nhập nội dung")));
        }
        else if (expectedResult==4){
            onView(withId(com.google.android.material.R.id.snackbar_text))
                    .check(matches(withText("Hãy nhập điểm")));
        }
        else if (expectedResult==5){
            onView(withId(com.google.android.material.R.id.snackbar_text))
                    .check(matches(withText("Điểm phải từ 0-10")));
        }

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
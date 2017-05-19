package com.silk.smartdoc;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;

import com.silk.smartdoc.View.TestSearchActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.silk.smartdoc.R.id.searchTestListView;

/**
 * Created by SOURABH on 02-05-2017.
 */


@RunWith(AndroidJUnit4.class)
public class SearchTestsActivityTest {

    @Rule public final ActivityRule<TestSearchActivity> main = new ActivityRule<>(TestSearchActivity.class);

    @Test
    public void shouldOpenTestSearchResultActivity() throws Exception {
        onView(withId(searchTestListView)).check(ViewAssertions.matches(isDisplayed()));

    }
}

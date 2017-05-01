package com.silk.smartdoc;

import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;

import com.silk.smartdoc.View.MedicineSearch;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.silk.smartdoc.R.id.searchListView;

@RunWith(AndroidJUnit4.class)
/**
 * Created by dodobhoot on 5/1/2017.
 */

public class SearchMedicineActivityTest {
    @Rule public final ActivityRule<MedicineSearch> main = new ActivityRule<>(MedicineSearch.class);

    @Test
    public void shouldOpenMedicneResult() throws Exception {
        onView(withId(searchListView)).check(ViewAssertions.matches(isDisplayed()));

    }
}

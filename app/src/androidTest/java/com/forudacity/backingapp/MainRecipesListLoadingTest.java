package com.forudacity.backingapp;

import android.content.Context;
import android.widget.TextView;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.forudacity.backingapp.utils.EspressoIdlingResource;
import com.forudacity.backingapp.view.main.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(AndroidJUnit4.class)
public class MainRecipesListLoadingTest {

    private boolean isTablet ;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource);
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        isTablet = targetContext.getResources().getBoolean(R.bool.IsTablet);
    }


    @After
    public void unregisterIdlingResource(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource);
    }


    @Test
    public void checkRecipesRecyclerViewIsVisible(){
        onView(withId(R.id.MainActivity)).check(matches(isDisplayed()));

        if (isTablet) {
            onView(withId(R.id.BackingCardsGridRecyclerView)).check(matches(isDisplayed()));
        }else{
            onView(withId(R.id.BackingCardsRecyclerView)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void  checkOnRecyclerViewItemClickedGoToStepsActivityWithTitle(){

        int list_item_position = 0;

        if (isTablet) {
            onView(withId(R.id.BackingCardsGridRecyclerView)).check(matches(isDisplayed()));
            onView(withId(R.id.BackingCardsGridRecyclerView)).perform(RecyclerViewActions.scrollToPosition(list_item_position));
            onView(withRecyclerView(R.id.BackingCardsGridRecyclerView).atPosition(list_item_position)).check(matches(isDisplayed()));
            onView(withRecyclerView(R.id.BackingCardsGridRecyclerView).atPosition(list_item_position)).perform(click());
        } else {
            onView(withId(R.id.BackingCardsRecyclerView)).check(matches(isDisplayed()));
            onView(withId(R.id.BackingCardsRecyclerView)).perform(RecyclerViewActions.scrollToPosition(list_item_position));
            onView(withRecyclerView(R.id.BackingCardsRecyclerView).atPosition(list_item_position)).check(matches(isDisplayed()));
            onView(withRecyclerView(R.id.BackingCardsRecyclerView).atPosition(list_item_position)).perform(click());
        }

        onView(withId(R.id.stepsListFragment)).check(matches(isDisplayed()));
        onView(allOf(instanceOf(TextView.class),
                withParent(withResourceName("action_bar"))))
                .check(matches(withText("Nutella Pie")));

    }


    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }


}

package com.example.samo20;


import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ComposerTest {
    // Den her test virker kun hvis applikationen initieres fra Composer og ikke fra Loading

    @Rule
    public ActivityTestRule<Composer> mActivityTestRule = new ActivityTestRule<>(Composer.class);

    @Test
    public void composerTestButtonsExists() {
        ViewInteraction button = onView(
                allOf(withId(R.id.heart), isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button1 = onView(
                allOf(withId(R.id.moon), isDisplayed()));
        button1.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.gift), isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.rain), isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction button4 = onView(
                allOf(withId(R.id.bug), isDisplayed()));
        button4.check(matches(isDisplayed()));

        ViewInteraction button5 = onView(
                allOf(withId(R.id.sun), isDisplayed()));
        button5.check(matches(isDisplayed()));

        ViewInteraction button6 = onView(
                allOf(withId(R.id.rainbow), isDisplayed()));
        button6.check(matches(isDisplayed()));

        ViewInteraction button7 = onView(
                allOf(withId(R.id.flower), isDisplayed()));
        button7.check(matches(isDisplayed()));

        ViewInteraction button8 = onView(
                allOf(withId(R.id.star), isDisplayed()));
        button8.check(matches(isDisplayed()));

        ViewInteraction button9 = onView(
                allOf(withId(R.id.play), isDisplayed()));
        button9.check(matches(isDisplayed()));
    }

    @Test
    public void composerTestButtonsAreClickable() {
        ViewInteraction button = onView(
                allOf(withId(R.id.heart), isClickable()));
        button.check(matches(isClickable()));

        ViewInteraction button1 = onView(
                allOf(withId(R.id.moon), isClickable()));
        button1.check(matches(isClickable()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.gift), isClickable()));
        button2.check(matches(isClickable()));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.rain), isClickable()));
        button3.check(matches(isClickable()));

        ViewInteraction button4 = onView(
                allOf(withId(R.id.bug), isClickable()));
        button4.check(matches(isClickable()));

        ViewInteraction button5 = onView(
                allOf(withId(R.id.sun), isClickable()));
        button5.check(matches(isClickable()));

        ViewInteraction button6 = onView(
                allOf(withId(R.id.rainbow), isClickable()));
        button6.check(matches(isClickable()));

        ViewInteraction button7 = onView(
                allOf(withId(R.id.flower), isClickable()));
        button7.check(matches(isClickable()));

        ViewInteraction button8 = onView(
                allOf(withId(R.id.star), isClickable()));
        button8.check(matches(isClickable()));
    }

    @Test
    public void playButtonNotEnabled_whenEmpty()
    {
        ViewInteraction button = onView(
                allOf(withId(R.id.play)));
        button.check(matches(not(isEnabled())));
    }
}

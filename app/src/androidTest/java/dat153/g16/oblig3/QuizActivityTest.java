package dat153.g16.oblig3;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.core.Is.is;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dat153.g16.oblig3.activity.QuizActivity;

@RunWith(AndroidJUnit4.class)
public class QuizActivityTest {
    @Rule
    public ActivityScenarioRule<QuizActivity> activityRule = new ActivityScenarioRule<>(QuizActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void scoreUpdatesCorrectlyOnCorrectAnswer() throws InterruptedException {
        String currentScoreText = "Score: 0";
        String expectedScoreText = "Score: 1";

        // Delay 100ms
        Thread.sleep(500);

        onView(withId(R.id.text_score)).check(matches(withText(currentScoreText)));
        onView(withTagValue(is("correctAnswer"))).perform(click());

        onView(withId(R.id.text_score)).check(matches(withText(expectedScoreText)));
    }
}

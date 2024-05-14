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

// Bruker AndroidJUnit4 som testkjører for å kjøre Espresso-tester
@RunWith(AndroidJUnit4.class)
public class QuizActivityTest {
    // Starter QuizActivity før hver test gjennom ActivityScenarioRule
    @Rule
    public ActivityScenarioRule<QuizActivity> activityRule = new ActivityScenarioRule<>(QuizActivity.class);

    // Initialiserer Intents før hver test
    @Before
    public void setUp() {
        Intents.init();
    }

    // Frigjør Intents etter hver test for å unngå minnelekkasje
    @After
    public void tearDown() {
        Intents.release();
    }

    // Tester om poengsummen oppdateres korrekt når brukeren trykker på det riktige svaret
    @Test
    public void scoreUpdatesCorrectlyOnCorrectAnswer() throws InterruptedException {
        // Definerer variabler for testen
        String currentScoreText = "Score: 0"; // Forventet startpoengsum
        String expectedScoreText = "Score: 1"; // Forventet poengsum etter korrekt svar

        // Venter 500ms for å sikre at databasen og UI har lastet fullstendig
        Thread.sleep(500);

        // Sjekker den initielle poengsummen og utfører klikk-handlingen på knappen med riktig svar
        onView(withId(R.id.text_score)).check(matches(withText(currentScoreText)));
        onView(withTagValue(is("correctAnswer"))).perform(click());

        // Sjekker om poengsummen er oppdatert korrekt
        onView(withId(R.id.text_score)).check(matches(withText(expectedScoreText)));
    }
}


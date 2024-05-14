package dat153.g16.oblig3;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dat153.g16.oblig3.activity.MainActivity;
import dat153.g16.oblig3.activity.QuizActivity;

// Angir at denne klassen bruker AndroidJUnit4 testkjører for å kjøre testene
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    // Definerer en regel som sørger for at MainActivity startes opp før hver test kjøres
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    // Metode som kjøres før hver test for å initialisere Intents, en del av Android Testing Support Library
    @Before
    public void setUp() {
        Intents.init();
    }

    // Metode som kjøres etter hver test for å frigjøre ressurser brukt av Intents
    @After
    public void tearDown() {
        Intents.release();
    }

    // En testmetode som sjekker om navigasjonen til QuizActivity fungerer som den skal
    @Test
    public void navigateToQuizActivity() {
        // Utfører et klikk på knappen med ID 'start_quiz'
        onView(withId(R.id.start_quiz)).perform(click());

        // Sjekker om den faktiske komponenten som er aktivert er QuizActivity
        intended(hasComponent(QuizActivity.class.getName()));
    }
}


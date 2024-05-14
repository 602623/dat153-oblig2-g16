package dat153.g16.oblig3;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.MatcherAssert.assertThat;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.GridView;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dat153.g16.oblig3.activity.GalleryActivity;

// Klassenotering for å spesifisere bruken av AndroidJUnit4 testkjører
@RunWith(AndroidJUnit4.class)
public class GalleryActivityTest {

    // Regel for å starte GalleryActivity før hver test
    @Rule
    public ActivityScenarioRule<GalleryActivity> activityScenarioRule = new ActivityScenarioRule<>(GalleryActivity.class);

    // Oppsettmetode kalt før hver test. Initialiserer Intents-rammeverket
    @Before
    public void setUp() {
        Intents.init();
    }

    // cleanup metode som blir kalt etter hver test. Frigjør Intents-rammeverket for å unngå minnelekkasjer.
    @After
    public void tearDown() {
        Intents.release();
    }

    // Test for å sikre at antallet bilder minker etter at et bilde er slettet
    @Test
    public void imageCountDecreaseAfterDeletion() {
        // Bruker en atomisk variabel for å holde det opprinnelige antallet bilder
        final int[] initialCount = new int[1];
        // Sjekk det opprinnelige antallet ved å finne GridView og få dens antall
        onView(withId(R.id.gallery_layout)).check(((view, noViewFoundException) -> {
            initialCount[0] = getCount(view);
        }));

        // Klikk på det første elementet
        onData(anything()).inAdapterView(withId(R.id.gallery_layout)).atPosition(0).perform(click());

        // Definer en tilpasset matcher for testen
        onView(withId(R.id.gallery_layout)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                // Sjekk om det nye antallet er 1 mindre
                return getCount(item) == initialCount[0] - 1;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("GridView skal ha 1 færre element etter sletting");
            }
        }));
    }

    // Test for å se om antall spørsmål øker etter å ha lagt til et spørsmål
    // Lager et mock data med et bilde av en katt. Den lagres i URI
    @Test
    public void QuestionCountIncreasesAfterAdding() throws InterruptedException {
        // Definer dataene som brukes for å etterligne
        int imageResource = R.drawable.cat;
        String packageName = getApplicationContext().getPackageName();
        Uri imageUri = Uri.parse("android.resource://" + packageName + "/" + imageResource);

        // Få det opprinnelige antallet spørsmål - bruker atomisk variabel
        final int[] initialCount = new int[1];
        onView(withId(R.id.gallery_layout)).check((view, noViewFoundException) -> {
            initialCount[0] = getCount(view);
        });

        // Klikk på en knapp for å gå til neste side, og vent 500 ms for å sikre at den nye siden er lastet
        onView(withId(R.id.button)).perform(click());
        Thread.sleep(500);

        // Definer og bruk mock-dataene
        Intent resultData = new Intent();
        resultData.setData(imageUri);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        intending(hasAction(Intent.ACTION_OPEN_DOCUMENT)).respondWith(result);

        // Utfør inndataene, lagre spørsmålet, og vent 500 ms for å sikre at den nye siden er lastet
        onView(withId(R.id.select_image_button)).perform(click());
        onView(withId(R.id.question_text)).perform(typeText("New Question"), closeSoftKeyboard());
        onView(withId(R.id.save_question)).perform(click());
        Thread.sleep(500);

        // Definer en tilpasset matcher for testen
        onView(withId(R.id.gallery_layout)).check((view, noViewFoundException) -> {
            TypeSafeMatcher<View> matcher = new TypeSafeMatcher<View>() {
                @Override
                protected boolean matchesSafely(View item) {
                    // Sjekk om det nye antallet er 1 mer
                    return getCount(view) == initialCount[0] + 1;
                }

                @Override
                public void describeTo(Description description) {
                    description.appendText("GridView skal ha ett mer element etter å ha lagt til et spørsmål");
                }
            };

            assertThat(view, matcher);
        });
    }

    // Hjelpefunksjon for å få antall elementer fra en GridView
    private int getCount(View view) {
        GridView gridView = (GridView) view;
        return gridView.getAdapter().getCount();
    }
}

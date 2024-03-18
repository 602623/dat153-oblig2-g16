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

@RunWith(AndroidJUnit4.class)
public class GalleryActivityTest {
    @Rule
    public ActivityScenarioRule<GalleryActivity> activityScenarioRule = new ActivityScenarioRule<>(GalleryActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void imageCountDecreaseAfterDeletion() {
        final int[] initialCount = new int[1];
        onView(withId(R.id.gallery_layout)).check(((view, noViewFoundException) -> {
            initialCount[0] = getCount(view);
        }));

        onData(anything()).inAdapterView(withId(R.id.gallery_layout)).atPosition(0).perform(click());

        onView(withId(R.id.gallery_layout)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                return getCount(item) == initialCount[0] - 1;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("GridView should have 1 less element after deletion");
            }
        }));
    }

    @Test
    public void QuestionCountIncreasesAfterAdding() throws InterruptedException {
        int imageResource = R.drawable.cat;
        String packageName = getApplicationContext().getPackageName();
        Uri imageUri = Uri.parse("android.resource://" + packageName + "/" + imageResource);

        // Get initial question count
        final int[] initialCount = new int[1];
        onView(withId(R.id.gallery_layout)).check((view, noViewFoundException) -> {
            initialCount[0] = getCount(view);
        });

        onView(withId(R.id.button)).perform(click());
        Thread.sleep(500);

        Intent resultData = new Intent();
        resultData.setData(imageUri);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        intending(hasAction(Intent.ACTION_OPEN_DOCUMENT)).respondWith(result);

        onView(withId(R.id.select_image_button)).perform(click());
        onView(withId(R.id.question_text)).perform(typeText("New Question"), closeSoftKeyboard());
        onView(withId(R.id.save_question)).perform(click());
        Thread.sleep(500);

        onView(withId(R.id.gallery_layout)).check((view, noViewFoundException) -> {
            TypeSafeMatcher<View> matcher = new TypeSafeMatcher<View>() {
                @Override
                protected boolean matchesSafely(View item) {
                    return getCount(view) == initialCount[0] + 1;
                }

                @Override
                public void describeTo(Description description) {
                    description.appendText("GridView should have one more item after adding a question");
                }
            };

            assertThat(view, matcher);
        });
    }

    private int getCount(View view) {
        GridView gridView = (GridView) view;
        return gridView.getAdapter().getCount();
    }
}
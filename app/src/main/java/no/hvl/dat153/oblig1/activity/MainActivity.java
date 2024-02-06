package no.hvl.dat153.oblig1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;

import no.hvl.dat153.oblig1.MyApp;
import no.hvl.dat153.oblig1.R;
import no.hvl.dat153.oblig1.model.Question;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyApp app = (MyApp) getApplication();
        List<Question> questions = app.getQuestions();

        // Setup the default questions
        if (questions.isEmpty()) {
            app.addQuestion(new Question(R.drawable.cat, "cat", app.getLength()));
            app.addQuestion(new Question(R.drawable.coala, "coala", app.getLength()));
            app.addQuestion(new Question(R.drawable.dog, "dog", app.getLength()));
        }

        // Get the components (buttons)
        Button startQuiz = findViewById(R.id.start_quiz);
        Button addPhoto = findViewById(R.id.add_photo);

        // Shuffle the questions, so they are in a random order
        startQuiz.setOnClickListener(v -> {
            Collections.shuffle(questions);
            Intent intent = new Intent(this, QuizActivity.class);
            intent.putExtra("index", 0); // always start at the first random question
            startActivity(intent);
        });

        // Redirect to the gallery-activity
        addPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddQuestionActivity.class);
            startActivity(intent);
        });
    }
}
package no.hvl.dat153.oblig1.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import no.hvl.dat153.oblig1.MyApp;
import no.hvl.dat153.oblig1.R;
import no.hvl.dat153.oblig1.model.Question;

public class ScoreActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        // Get the global application object and get all the questions
        MyApp app = (MyApp) getApplication();
        List<Question> questions = app.getQuestions();

        // Find the text-view and set the score
        TextView view = findViewById(R.id.text_view);
        view.setText(String.valueOf(questions.stream().filter(Question::isAnsweredCorrectly).count()));
    }
}

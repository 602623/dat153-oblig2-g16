package dat153.g16.oblig3.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import dat153.g16.oblig3.MyApp;
import dat153.g16.oblig3.R;
import dat153.g16.oblig3.model.Question;

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

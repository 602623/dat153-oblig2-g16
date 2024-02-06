package no.hvl.dat153.oblig1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import no.hvl.dat153.oblig1.MyApp;
import no.hvl.dat153.oblig1.R;
import no.hvl.dat153.oblig1.model.Question;

public class QuizActivity extends AppCompatActivity {
    private List<Question> questions;
    private int index;
    private final List<Button> buttons = new ArrayList<>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        MyApp app = (MyApp) getApplication();
        questions = app.getQuestions();

        // Store the index from the intent
        index = getIntent().getIntExtra("index", 0);

        // Get the view components
        ImageView imageView = findViewById(R.id.image_view);
        TextView textView = findViewById(R.id.text_view);

        // Set the text for the quiz-progress
        textView.setText(String.format(Locale.getDefault(), "%d / %d", index + 1, questions.size()));

        // Render the bitmap or image-resource
        Question question = questions.get(index);
        if (question.isUsingBitmap()) {
            imageView.setImageBitmap(question.getBitmap());
        } else {
            imageView.setImageResource(question.getImageResId());
        }

        // Get the shuffled answers
        List<String> answers = getAnswers(questions.get(index));

        // Define answer buttons
        buttons.add(findViewById(R.id.answer1));
        buttons.add(findViewById(R.id.answer2));
        buttons.add(findViewById(R.id.answer3));

        // Set the handler and text for each answer-button
        for (int i = 0; i < buttons.size(); i++) {
            Button button = buttons.get(i);
            String answer = answers.get(i);

            button.setText(answer);
            handleClick(button, answer);
        }

    }

    private void handleClick(Button button, String answer) {
        Question askedQuestion = questions.get(index);
        String correctAnswer = askedQuestion.getAnswer();

        Button nextButton = findViewById(R.id.next);
        nextButton.setOnClickListener(v -> {
            // redirect to next question, or score page'
            Intent intent = new Intent(this, ScoreActivity.class);
            if (index < questions.size() - 1) {
                // Redirect to the next question
                intent = new Intent(this, this.getClass());
                intent.putExtra("index", index + 1);
            }

            // Perform the redirect
            startActivity(intent);
            finish();
        });

        button.setOnClickListener(v -> {
            // Check if the correct answer was clicked
            if (answer.equals(correctAnswer)) {
                askedQuestion.setAnsweredCorrectly(true);
            }

            buttons.forEach(b -> {
                b.setEnabled(false); // Disable all buttons

                // Indicate the correct answer and the clicked answer using colors
                if (b.getText().equals(correctAnswer)) {
                    b.setBackgroundColor(getColor(R.color.correct));
                    b.setTextColor(getColor(R.color.white));
                } else if (b.getText().equals(answer)) {
                    b.setBackgroundColor(getColor(R.color.incorrect));
                    b.setTextColor(getColor(R.color.white));
                }
            });

            nextButton.setEnabled(true); // Enable the next button
        });
    }

    private List<String> getAnswers(Question question) {
        // Get 2 invalid answers
        List<String> answers = questions.stream()
                .filter(q -> !q.equals(question))
                .limit(2)
                .map(Question::getAnswer)
                .collect(Collectors.toList());

        // add the correct answer
        answers.add(question.getAnswer());

        // shuffle the answers
        Collections.shuffle(answers);

        return answers;
    }
}
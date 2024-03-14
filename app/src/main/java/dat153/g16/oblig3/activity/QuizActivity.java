package dat153.g16.oblig3.activity;

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
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import dat153.g16.oblig3.MyApp;
import dat153.g16.oblig3.R;
import dat153.g16.oblig3.model.Question;
import dat153.g16.oblig3.model.QuestionRepo;

public class QuizActivity extends AppCompatActivity {
    private Question askedQuestion;
    private int index;
    private final List<Button> buttons = new ArrayList<>(3);
    private QuestionRepo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        repo = QuestionRepo.getInstance(getApplication());

        // Store the index from the intent
        index = getIntent().getIntExtra("index", 0);

        // Get the view components
        ImageView imageView = findViewById(R.id.image_view);
        TextView textView = findViewById(R.id.text_view);

        // Set the text for the quiz-progress
        textView.setText(String.format(Locale.getDefault(), "question nr.%d", index + 1));

        // Add score-label
        setScore();

        // Render the bitmap or image-resource
        repo.getRandomQuestions().observe(this, questions -> {
            if (questions.size() == 0) return;
            Question question = questions.stream().filter(q -> !q.isAnswered()).findFirst().orElse(null);

            if (question == null) {
                Intent intent = new Intent(this, ScoreActivity.class);
                startActivity(intent);
                finish();
                return;
            }

            if (question.isUsingUri()) {
                imageView.setImageURI(question.getUri());
            } else {
                imageView.setImageResource(question.getImageResId());
            }

            // Get the shuffled answers
            getAnswers(question, answers -> {
                askedQuestion = question;

                // Define answer buttons
                buttons.add(findViewById(R.id.answer1));
                buttons.add(findViewById(R.id.answer2));
                buttons.add(findViewById(R.id.answer3));

                String correctAnswer = answers.get(0);
                List<String> remainingAnswers = answers.subList(1, answers.size());
                Collections.shuffle(remainingAnswers);

                int length = Math.min(buttons.size(), answers.size());
                int randomPos = random(0, length - 1);

                // Set the handler and text for each answer-button
                for (int i = 0, j = 0; i < length; i++) {
                    Button button = buttons.get(i);
                    String answer;

                    if (i == randomPos) {
                        answer = correctAnswer;
                        button.setTag("correctAnswer");
                    } else {
                        answer = remainingAnswers.get(j++);
                    }

                    button.setText(answer);
                    handleClick(button, answer);
                }
            });
        });

    }

    private void handleClick(Button button, String answer) {
        String correctAnswer = askedQuestion.getAnswer();

        Button nextButton = findViewById(R.id.next);
        nextButton.setOnClickListener(v -> {
            repo.update(askedQuestion);

            // redirect to next question
            Intent intent = new Intent(this, this.getClass());
            intent.putExtra("index", index + 1);

            // Perform the redirect
            startActivity(intent);
            finish();
        });

        button.setOnClickListener(v -> {
            // Check if the correct answer was clicked
            if (answer.equals(correctAnswer)) {
                MyApp app = (MyApp) getApplication();
                app.incrementScore();

                setScore();
            }

            buttons.forEach(b -> {
                b.setEnabled(false); // Disable all buttons

                // Indicate the correct answer and the clicked answer using colors
                if (b.getText().equals(correctAnswer)) {
                    b.setBackgroundColor(getColor(R.color.correct));
                    b.setTextColor(getColor(R.color.white));

                    askedQuestion.setAnswered(true);
                } else if (b.getText().equals(answer)) {
                    b.setBackgroundColor(getColor(R.color.incorrect));
                    b.setTextColor(getColor(R.color.white));
                }
            });

            nextButton.setEnabled(true); // Enable the next button
        });
    }

    private void getAnswers(Question question, Consumer<List<String>> callback) {
        repo.getAllQuestions().observe(this, questions -> {
            List<String> answers = new ArrayList<>();

            // add the correct answer
            answers.add(question.getAnswer());

            List<String> wrongAnswers = questions.stream()
                    .filter(q -> !q.equals(question))
                    .limit(2)
                    .map(Question::getAnswer)
                    .collect(Collectors.toList());

            answers.addAll(wrongAnswers);

            callback.accept(answers);
        });
    }

    private int random(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

    private void setScore() {
        MyApp app = (MyApp) getApplication();

        TextView textScore = findViewById(R.id.text_score);
        textScore.setText(String.format(Locale.getDefault(), "Score: %d", app.getScore()));
    }
}
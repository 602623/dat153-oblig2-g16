package dat153.g16.oblig3;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import dat153.g16.oblig3.model.Question;

public class MyApp extends Application {
    private List<Question> questions = new ArrayList<>();
    private int score = 0;

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }

    public void resetScore() {
        score = 0;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

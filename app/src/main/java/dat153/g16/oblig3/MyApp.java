package dat153.g16.oblig3;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import dat153.g16.oblig3.model.Question;

public class MyApp extends Application {
    private final List<Question> questions = new ArrayList<>();
    private int length = 0;
    private boolean reverseSort = false;

    @Override
    public void onCreate() {
        super.onCreate();

        addQuestion(new Question(R.drawable.cat, "cat", getLength()));
        addQuestion(new Question(R.drawable.coala, "coala", getLength()));
        addQuestion(new Question(R.drawable.dog, "dog", getLength()));
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    // Method to get & increment the index to prevent duplicate question-ids
    public int getLength() {
        int len = length;
        length++;

        return len;
    }

    public boolean isReverseSort() {
        return reverseSort;
    }

    public void setReverseSort(boolean reverseSort) {
        this.reverseSort = reverseSort;
    }
}

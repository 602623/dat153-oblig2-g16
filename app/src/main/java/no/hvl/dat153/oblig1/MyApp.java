package no.hvl.dat153.oblig1;

import android.app.Application;

import java.util.ArrayList;

import no.hvl.dat153.oblig1.model.Question;

public class MyApp extends Application {
    private final ArrayList<Question> questions = new ArrayList<>();
    private int length = 0;

    public ArrayList<Question> getQuestions() {
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
}

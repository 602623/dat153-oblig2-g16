package dat153.g16.oblig3.model;

import android.net.Uri;

public class Question {
    private int resId = 0;
    private Uri uri = null;
    private final String answer;
    private boolean answeredCorrectly = false;
    private final int id;

    public Question(int resId, String name, int id) {
        this.resId = resId;
        this.answer = name;
        this.id = id;
    }

    public Question(Uri uri, String name, int id) {
        this.uri = uri;
        this.answer = name;
        this.id = id;
    }

    public int getImageResId() {
        return resId;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isAnsweredCorrectly() {
        return answeredCorrectly;
    }

    public void setAnsweredCorrectly(boolean answeredCorrectly) {
        this.answeredCorrectly = answeredCorrectly;
    }

    public Uri getUri() {
        return uri;
    }

    public boolean isUsingUri() {
        return uri != null;
    }

    public int getId() {
        return id;
    }
}

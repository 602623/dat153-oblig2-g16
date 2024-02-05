package no.hvl.dat153.oblig1.model;

import android.graphics.Bitmap;

public class Question {
    private int imageResId = 0;
    private Bitmap bitmap = null;
    private final String answer;
    private boolean answeredCorrectly = false;
    private final int id;

    public Question(int resId, String name, int id) {
        this.imageResId = resId;
        this.answer = name;
        this.id = id;
    }

    public Question(Bitmap bitmap, String name, int id) {
        this.bitmap = bitmap;
        this.answer = name;
        this.id = id;
    }

    public int getImageResId() {
        return imageResId;
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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public boolean isUsingBitmap() {
        return bitmap != null;
    }

    public int getId() {
        return id;
    }
}

package dat153.g16.oblig3;

import android.app.Application;

public class MyApp extends Application {
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
}

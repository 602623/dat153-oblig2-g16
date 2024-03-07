package dat153.g16.oblig3.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import dat153.g16.oblig3.MyApp;
import dat153.g16.oblig3.R;

public class ScoreActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        MyApp app = (MyApp) getApplication();

        // Find and set the score
        TextView view = findViewById(R.id.text_view);
        view.setText(String.valueOf(app.getScore()));
    }
}

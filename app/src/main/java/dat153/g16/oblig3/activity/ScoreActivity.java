package dat153.g16.oblig3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
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

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}

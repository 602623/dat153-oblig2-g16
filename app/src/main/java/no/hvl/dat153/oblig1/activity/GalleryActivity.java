package no.hvl.dat153.oblig1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Comparator;
import java.util.List;

import no.hvl.dat153.oblig1.MyApp;
import no.hvl.dat153.oblig1.R;
import no.hvl.dat153.oblig1.adapter.ImageAdapter;
import no.hvl.dat153.oblig1.model.Question;

public class GalleryActivity extends AppCompatActivity {
    private List<Question> questions;
    private ActivityResultLauncher<Intent> addQuestionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // Get the sort buttons
        Button sortAscButton = findViewById(R.id.buttonAsc);
        Button sortDescButton = findViewById(R.id.buttonDesc);

        MyApp app = (MyApp) getApplication();
        questions = app.getQuestions();
        if (!app.isReverseSort()) {
            toggleButtons(sortAscButton, sortDescButton);
            questions.sort(Comparator.comparing(Question::getAnswer));
        } else {
            toggleButtons(sortAscButton, sortDescButton);
            questions.sort(Comparator.comparing(Question::getAnswer).reversed());
        }

        // Get the components
        FloatingActionButton button = findViewById(R.id.button);
        GridView gridView = findViewById(R.id.gallery_layout);

        // Use a custom adapter to display the images with text
        gridView.setAdapter(new ImageAdapter(this, questions));

        // Reload and remove the current activity when a new question is added
        addQuestionLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        startActivity(getIntent());
                        finish();
                    }
                }
        );

        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddQuestionActivity.class);
            addQuestionLauncher.launch(intent);
        });


        // Sort the questions ascending
        sortAscButton.setOnClickListener(v -> {
            questions.sort(Comparator.comparing(Question::getAnswer));
            gridView.setAdapter(new ImageAdapter(this, questions));
            toggleButtons(sortAscButton, sortDescButton);
            app.setReverseSort(false);
        });

        // Sort the questions descending
        sortDescButton.setOnClickListener(v -> {
            questions.sort(Comparator.comparing(Question::getAnswer).reversed());
            gridView.setAdapter(new ImageAdapter(this, questions));
            toggleButtons(sortDescButton, sortAscButton);
            app.setReverseSort(true);
        });
    }

    private void toggleButtons(Button btn1, Button btn2) {
        btn1.setEnabled(!btn1.isEnabled());
        btn2.setEnabled(!btn2.isEnabled());
    }
}

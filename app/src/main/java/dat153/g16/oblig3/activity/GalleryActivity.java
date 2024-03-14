package dat153.g16.oblig3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Comparator;

import dat153.g16.oblig3.R;
import dat153.g16.oblig3.adapter.ImageAdapter;
import dat153.g16.oblig3.model.Question;
import dat153.g16.oblig3.model.QuestionRepo;

public class GalleryActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> addQuestionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // Get the sort buttons
        Button sortAscButton = findViewById(R.id.buttonAsc);
        Button sortDescButton = findViewById(R.id.buttonDesc);

        // Get the components
        FloatingActionButton button = findViewById(R.id.button);
        GridView gridView = findViewById(R.id.gallery_layout);

        QuestionRepo repo = QuestionRepo.getInstance(getApplication());
        repo.getAllQuestions().observe(this, questions -> {
            // Initialize the buttons to the correct state
            sortDescButton.setEnabled(true);
            sortAscButton.setEnabled(false);

            // Set the default sort order
            questions.sort(Comparator.comparing(Question::getAnswer));
            gridView.setAdapter(new ImageAdapter(this, questions));
        });

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Question questionToDelete = (Question) parent.getItemAtPosition(position);

            repo.delete(questionToDelete);
        });

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

        repo.getAllQuestions().observe(this, questions -> {
            // Sort the questions ascending
            sortAscButton.setOnClickListener(v -> {
                questions.sort(Comparator.comparing(Question::getAnswer));
                gridView.setAdapter(new ImageAdapter(this, questions));
                toggleButtons(sortAscButton, sortDescButton);
            });

            // Sort the questions descending
            sortDescButton.setOnClickListener(v -> {
                questions.sort(Comparator.comparing(Question::getAnswer).reversed());
                gridView.setAdapter(new ImageAdapter(this, questions));
                toggleButtons(sortDescButton, sortAscButton);
            });
        });
    }

    private void toggleButtons(Button btn1, Button btn2) {
        btn1.setEnabled(!btn1.isEnabled());
        btn2.setEnabled(!btn2.isEnabled());
    }
}

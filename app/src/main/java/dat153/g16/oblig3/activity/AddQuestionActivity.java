package dat153.g16.oblig3.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import dat153.g16.oblig3.R;
import dat153.g16.oblig3.model.Question;
import dat153.g16.oblig3.model.QuestionRepo;

public class AddQuestionActivity extends AppCompatActivity {
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        // Get the components
        EditText questionText = findViewById(R.id.question_text);
        Button selectImageButton = findViewById(R.id.select_image_button);
        Button saveButton = findViewById(R.id.save_question);

        // Register a new activity to pick an image
        ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();

                        final int takeFlags = (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        getContentResolver().takePersistableUriPermission(imageUri, takeFlags);
                    }
                }
        );

        // Start the new activity as defined above
        selectImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            pickImageLauncher.launch(intent);
        });

        // Save the question when the button is clicked
        saveButton.setOnClickListener(v -> saveQuestion(questionText.getText().toString(), imageUri));
    }

    private void saveQuestion(String text, Uri image) {
        // Only save the question if text and image is not null
        if (text.length() > 0 && image != null) {
            QuestionRepo repo = QuestionRepo.getInstance(getApplication());
            repo.insert(new Question(image.toString(), text));

            // Redirect to the gallery activity
            Intent intent = new Intent(this, GalleryActivity.class);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}

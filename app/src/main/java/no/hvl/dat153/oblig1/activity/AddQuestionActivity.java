package no.hvl.dat153.oblig1.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import no.hvl.dat153.oblig1.MyApp;
import no.hvl.dat153.oblig1.R;
import no.hvl.dat153.oblig1.model.Question;

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
                    if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                        imageUri = result.getData().getData();
                    }
                }
        );

        // Start the new activity as defined above
        selectImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        });

        // Save the question when the button is clicked
        saveButton.setOnClickListener(v -> saveQuestion(questionText.getText().toString(), imageUri));
    }

    // Get the bitmap from the imageUri using the ImageDecoder
    private Bitmap getBitmap(Uri uri) {
        ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), uri);
        try {
            return ImageDecoder.decodeBitmap(source);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void saveQuestion(String text, Uri image) {
        // Only save the question if text and image is not null
        if (text.length() > 0 && image != null) {
            Bitmap imageBitmap = getBitmap(image);

            // Only save the question if the bitmap is not null (bitmap was correctly decoded from the uri)
            if (imageBitmap != null) {
                MyApp app = (MyApp) getApplication();
                app.addQuestion(new Question(imageBitmap, text, app.getLength()));

                // Redirect to the gallery activity
                startActivity(new Intent(this, GalleryActivity.class));
                finish();
            }
        }
    }
}

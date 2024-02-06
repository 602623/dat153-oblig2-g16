package no.hvl.dat153.oblig1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Comparator;

import no.hvl.dat153.oblig1.MyApp;
import no.hvl.dat153.oblig1.R;
import no.hvl.dat153.oblig1.adapter.ImageAdapter;
import no.hvl.dat153.oblig1.model.Question;

public class GalleryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        MyApp app = (MyApp) getApplication();
        ArrayList<Question> questions = app.getQuestions();
        questions.sort(Comparator.comparingInt(Question::getId));

        // Get the components
        FloatingActionButton button = findViewById(R.id.button);
        GridView gridView = findViewById(R.id.gallery_layout);

        // Use a custom adapter to display the images with text
        gridView.setAdapter(new ImageAdapter(this, questions));

        button.setOnClickListener(v -> {
            startActivity(new Intent(this, AddQuestionActivity.class));
            finish();
        });
    }
}

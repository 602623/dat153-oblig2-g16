package no.hvl.dat153.oblig1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ImageView imageView = findViewById(R.id.image_view);
        TextView textView = findViewById(R.id.text_view);

        Collection<Object> images = new ArrayList<>();
        Collection<String> names = new ArrayList<>();
        images.add(R.drawable.dog);
        names.add("dog");
        images.add(R.drawable.coala);
        names.add("coala");
        images.add(R.drawable.cat);
        names.add("cat");

        if (images.size() != names.size()) {
            throw new RuntimeException("images and names must be the same size");
        }

        index = getIntent().getIntExtra("index", 0);

        textView.setText(String.format("%d / %d", index + 1, images.size()));
        imageView.setImageResource((Integer) images.toArray()[index]);

        Button increment = findViewById(R.id.increment);
        Button decrement = findViewById(R.id.decrement);

        if (index == 0) {
            decrement.setEnabled(false);
        } else {
            decrement.setEnabled(true);
        }

        if (index == images.size() - 1) {
            increment.setEnabled(false);
        } else {
            increment.setEnabled(true);
        }

        decrement.setOnClickListener(v -> {
            Intent intent = new Intent(this, this.getClass());
            intent.putExtra("index", index - 1);

            finish();
            startActivity(intent);
        });

        increment.setOnClickListener(v -> {
            Intent intent = new Intent(this, this.getClass());
            intent.putExtra("index", index + 1);

            finish();
            startActivity(intent);
        });
    }
}
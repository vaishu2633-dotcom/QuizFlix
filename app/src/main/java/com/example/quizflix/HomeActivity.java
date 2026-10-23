package com.example.quizflix;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

public class HomeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        View btnStart = findViewById(R.id.btnStartExploring);
        View cardGK = findViewById(R.id.cardGK);
        View cardCS = findViewById(R.id.cardCS);
        View cardAI = findViewById(R.id.cardAI);
        View cardMixed = findViewById(R.id.cardMixed);

        // Start Exploring opens category selection
        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(
                    HomeActivity.this,
                    CategoryActivity.class
            );
            startActivity(intent);
        });

        // Direct category selection
        cardGK.setOnClickListener(v -> openQuiz("GK"));
        cardCS.setOnClickListener(v -> openQuiz("CS"));
        cardAI.setOnClickListener(v -> openQuiz("AI"));
        cardMixed.setOnClickListener(v -> openQuiz("Mixed"));

        // TV remote focus
        btnStart.requestFocus();
    }

    private void openQuiz(String category) {
        Intent intent = new Intent(
                HomeActivity.this,
                MainActivity.class
        );

        intent.putExtra("CATEGORY", category);
        startActivity(intent);
    }
}

package com.example.quizflix;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.FragmentActivity;

public class CategoryActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        View btnGK = findViewById(R.id.btnGK);
        View btnCS = findViewById(R.id.btnCS);
        View btnAI = findViewById(R.id.btnAI);
        View btnMixed = findViewById(R.id.btnMixed);

        btnGK.setOnClickListener(v -> openQuiz("GK"));
        btnCS.setOnClickListener(v -> openQuiz("CS"));
        btnAI.setOnClickListener(v -> openQuiz("AI"));
        btnMixed.setOnClickListener(v -> openQuiz("Mixed"));

        btnGK.requestFocus();
    }

    private void openQuiz(String category) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("CATEGORY", category);
        startActivity(intent);
    }
}

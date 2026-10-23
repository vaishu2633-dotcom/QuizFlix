
package com.example.quizflix;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.util.List;

public class MainActivity extends FragmentActivity {

    // UI COMPONENTS
    private TextView tvQuestion, tvScore, tvTimer;
    private TextView tvInstructions;
    private ProgressBar quizProgress;

    private final Button[] buttons = new Button[4];
    private Button btnBackHome;

    // QUIZ DATA
    private List<QuestionBank.Question> questions;

    private int questionIndex = 0;
    private int score = 0;
    private int bestScore = 0;

    private boolean started = false;
    private boolean answerLocked = false;

    private String category = "Mixed";

    // TIMER SETTINGS
    private static final long QUESTION_TIME = 20000L;
    private static final long FEEDBACK_DELAY = 1200L;

    private CountDownTimer countDownTimer;

    private final Handler handler =
            new Handler(Looper.getMainLooper());

    private Runnable nextQuestionRunnable;

    // HIGH SCORE STORAGE
    private SharedPreferences highScorePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Initialize high score storage
        highScorePreferences = getSharedPreferences(
                "QuizFlixHighScores",
                MODE_PRIVATE
        );

        // Initialize views
        tvQuestion = findViewById(R.id.tvQuestion);
        tvScore = findViewById(R.id.tvScore);
        tvTimer = findViewById(R.id.tvTimer);
        tvInstructions = findViewById(R.id.tvInstructions);
        quizProgress = findViewById(R.id.quizProgress);

        buttons[0] = findViewById(R.id.btnOption1);
        buttons[1] = findViewById(R.id.btnOption2);
        buttons[2] = findViewById(R.id.btnOption3);
        buttons[3] = findViewById(R.id.btnOption4);

        btnBackHome = findViewById(R.id.btnBackHome);

        // Back to Home button
        btnBackHome.setOnClickListener(v -> {

            stopQuestionTimer();
            cancelPendingTransition();

            Intent intent = new Intent(
                    MainActivity.this,
                    HomeActivity.class
            );

            intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
            );

            startActivity(intent);
            finish();
        });

        // Get selected category
        String selectedCategory =
                getIntent().getStringExtra("CATEGORY");

        if (selectedCategory != null) {
            category = selectedCategory;
        }

        // Answer button listeners
        for (int i = 0; i < buttons.length; i++) {

            final int selected = i;

            buttons[i].setOnClickListener(v -> {

                if (!started) {

                    if (getIntent().hasExtra("CATEGORY")) {
                        startQuiz();
                    } else {

                        startActivity(new Intent(
                                MainActivity.this,
                                CategoryActivity.class
                        ));
                    }

                } else {
                    checkAnswer(selected);
                }
            });
        }

        if (getIntent().hasExtra("CATEGORY")) {
            startQuiz();
        } else {
            showWelcome();
        }
    }

    // WELCOME SCREEN
    private void showWelcome() {

        started = false;
        answerLocked = false;

        stopQuestionTimer();
        cancelPendingTransition();

        tvTimer.setVisibility(View.GONE);
        quizProgress.setVisibility(View.GONE);
        btnBackHome.setVisibility(View.GONE);

        tvQuestion.setTextSize(23);
        tvQuestion.setText("Welcome to QuizFlix!");

        tvScore.setText("Choose a category to begin.");

        buttons[0].setText("Choose Category");
        buttons[0].setVisibility(View.VISIBLE);
        buttons[0].setEnabled(true);

        for (int i = 1; i < buttons.length; i++) {
            buttons[i].setVisibility(View.GONE);
        }

        buttons[0].requestFocus();
    }

    // START NEW QUIZ
    private void startQuiz() {

        stopQuestionTimer();
        cancelPendingTransition();

        // Random questions
        questions = QuestionBank.getQuestions(category);

        // Load saved high score
        bestScore = highScorePreferences.getInt(
                "BEST_" + category,
                0
        );

        questionIndex = 0;
        score = 0;

        started = true;
        answerLocked = false;

        btnBackHome.setVisibility(View.GONE);

        quizProgress.setVisibility(View.VISIBLE);
        quizProgress.setProgress(0);

        tvTimer.setVisibility(View.VISIBLE);

        tvInstructions.setText(
                "Use arrows to navigate • Press Select to answer"
        );

        // Reset buttons
        for (Button button : buttons) {

            button.setBackgroundResource(
                    R.drawable.tv_answer_background
            );

            button.setEnabled(true);
        }

        showQuestion();
    }

    // DISPLAY QUESTION
    private void showQuestion() {

        stopQuestionTimer();

        if (questionIndex >= questions.size()) {
            showResult();
            return;
        }

        answerLocked = false;

        btnBackHome.setVisibility(View.GONE);

        tvQuestion.setTextSize(23);

        QuestionBank.Question question =
                questions.get(questionIndex);

        quizProgress.setVisibility(View.VISIBLE);

        quizProgress.setProgress(
                (questionIndex * 100) / questions.size()
        );

        String categoryName;

        switch (category) {

            case "GK":
                categoryName = "GENERAL KNOWLEDGE";
                break;

            case "CS":
                categoryName = "COMPUTER SCIENCE";
                break;

            case "AI":
                categoryName = "ARTIFICIAL INTELLIGENCE";
                break;

            default:
                categoryName = "MIXED CHALLENGE";
                break;
        }

        tvQuestion.setText(
                categoryName + "\n\n" +
                        "Question " + (questionIndex + 1) +
                        " of " + questions.size() + "\n\n" +
                        question.text
        );

        tvScore.setText(
                "Score: " + score +
                        "     |     Best Score: " + bestScore
        );

        // Display options
        for (int i = 0; i < buttons.length; i++) {

            buttons[i].setVisibility(View.VISIBLE);

            buttons[i].setText(
                    question.options[i]
            );

            buttons[i].setEnabled(true);

            buttons[i].setBackgroundResource(
                    R.drawable.tv_answer_background
            );
        }

        buttons[0].requestFocus();

        startQuestionTimer();
    }

    // START QUESTION TIMER
    private void startQuestionTimer() {

        stopQuestionTimer();

        tvTimer.setVisibility(View.VISIBLE);
        tvTimer.setText("Time Left: 20s");
        tvTimer.setTextColor(0xFF38BDF8);

        countDownTimer = new CountDownTimer(
                QUESTION_TIME,
                1000L
        ) {

            @Override
            public void onTick(long millisUntilFinished) {

                long seconds =
                        (millisUntilFinished + 999L) / 1000L;

                tvTimer.setText(
                        "Time Left: " + seconds + "s"
                );

                if (seconds <= 5) {

                    tvTimer.setTextColor(
                            0xFFF87171
                    );

                } else {

                    tvTimer.setTextColor(
                            0xFF38BDF8
                    );
                }
            }

            @Override
            public void onFinish() {

                countDownTimer = null;

                tvTimer.setText("Time's Up!");

                tvTimer.setTextColor(
                        0xFFF87171
                );

                handleTimeOut();
            }
        }.start();
    }

    // STOP QUESTION TIMER
    private void stopQuestionTimer() {

        if (countDownTimer != null) {

            countDownTimer.cancel();

            countDownTimer = null;
        }
    }

    // CHECK ANSWER
    private void checkAnswer(int selected) {

        if (!started || answerLocked) {
            return;
        }

        answerLocked = true;

        stopQuestionTimer();

        QuestionBank.Question question =
                questions.get(questionIndex);

        int correctAnswer = question.answer;

        boolean isCorrect =
                selected == correctAnswer;

        if (isCorrect) {

            score++;

            tvScore.setText(
                    "Correct! Score: " + score +
                            " | Best: " + bestScore
            );

        } else {

            tvScore.setText(
                    "Incorrect! Correct answer: " +
                            question.options[correctAnswer]
            );
        }

        // Disable buttons during feedback
        for (Button button : buttons) {
            button.setEnabled(false);
        }

        // Green correct answer
        buttons[correctAnswer].setBackgroundResource(
                R.drawable.tv_answer_correct
        );

        // Red wrong answer
        if (!isCorrect) {

            buttons[selected].setBackgroundResource(
                    R.drawable.tv_answer_wrong
            );
        }

        tvTimer.setText(
                isCorrect ?
                        "Correct Answer!" :
                        "Wrong Answer!"
        );

        scheduleNextQuestion();
    }

    // HANDLE TIMEOUT
    private void handleTimeOut() {

        if (!started || answerLocked) {
            return;
        }

        answerLocked = true;

        stopQuestionTimer();

        QuestionBank.Question question =
                questions.get(questionIndex);

        int correctAnswer = question.answer;

        tvScore.setText(
                "Time's Up! Correct answer: " +
                        question.options[correctAnswer]
        );

        for (Button button : buttons) {
            button.setEnabled(false);
        }

        buttons[correctAnswer].setBackgroundResource(
                R.drawable.tv_answer_correct
        );

        scheduleNextQuestion();
    }

    // MOVE TO NEXT QUESTION
    private void scheduleNextQuestion() {

        cancelPendingTransition();

        nextQuestionRunnable = () -> {

            nextQuestionRunnable = null;

            if (isFinishing() || isDestroyed()) {
                return;
            }

            questionIndex++;
            answerLocked = false;

            showQuestion();
        };

        handler.postDelayed(
                nextQuestionRunnable,
                FEEDBACK_DELAY
        );
    }

    // CANCEL PENDING TRANSITION
    private void cancelPendingTransition() {

        if (nextQuestionRunnable != null) {

            handler.removeCallbacks(
                    nextQuestionRunnable
            );

            nextQuestionRunnable = null;
        }
    }

    // DISPLAY FINAL RESULT
    private void showResult() {

        stopQuestionTimer();
        cancelPendingTransition();

        started = false;
        answerLocked = false;

        tvTimer.setVisibility(View.GONE);

        quizProgress.setVisibility(View.VISIBLE);
        quizProgress.setProgress(100);

        int percentage =
                (score * 100) / questions.size();

        // Check high score
        boolean newHighScore =
                score > bestScore;

        if (newHighScore) {

            bestScore = score;

            highScorePreferences.edit()
                    .putInt(
                            "BEST_" + category,
                            bestScore
                    )
                    .apply();
        }

        String resultMessage =
                "QUIZ COMPLETED!\n\n" +
                        "Your Score: " +
                        score + "/" + questions.size() +
                        "\n" +
                        "Percentage: " + percentage + "%" +
                        "\n\n" +
                        "Best Score: " +
                        bestScore + "/" + questions.size();

        if (newHighScore) {

            resultMessage +=
                    "\n\nNEW HIGH SCORE!";
        }

        tvQuestion.setTextSize(28);
        tvQuestion.setText(resultMessage);

        if (percentage >= 80) {

            tvScore.setText(
                    "Excellent Performance!"
            );

        } else if (percentage >= 50) {

            tvScore.setText(
                    "Good Job! Keep Practicing!"
            );

        } else {

            tvScore.setText(
                    "Keep Learning! Try Again!"
            );
        }

        // Play Again
        buttons[0].setText("Play Again");
        buttons[0].setVisibility(View.VISIBLE);
        buttons[0].setEnabled(true);

        buttons[0].setBackgroundResource(
                R.drawable.tv_answer_background
        );

        for (int i = 1; i < buttons.length; i++) {
            buttons[i].setVisibility(View.GONE);
        }

        // Show Back to Home button
        btnBackHome.setVisibility(View.VISIBLE);
        btnBackHome.setEnabled(true);

        tvInstructions.setText(
                "Press Select to play again or return home"
        );

        buttons[0].requestFocus();
    }

    // CLEAN UP RESOURCES
    @Override
    protected void onDestroy() {

        stopQuestionTimer();
        cancelPendingTransition();

        super.onDestroy();
    }
}

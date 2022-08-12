package com.josericardojr.quizgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.josericardojr.quizgame.databinding.ActivityMainBinding;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private QuizViewModel quizViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the ViewModel
        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        updateQuestion();

        binding.trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        binding.falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizViewModel.moveToNext();
                updateQuestion();
            }
        });
    }

    private void updateQuestion(){
        int questionTextResId = quizViewModel.currentQuestionText();
        binding.questionTextView.setText(questionTextResId);
    }

    private void checkAnswer(boolean userAnswer){
        boolean correct = quizViewModel.currentQuestionAnswer();

        int messageResId = userAnswer == correct ? R.string.correct_toast
                : R.string.incorrect_toast;

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}